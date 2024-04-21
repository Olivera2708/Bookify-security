import { Component, OnInit, ViewChild } from '@angular/core';
import { CertificateService } from '../certificate.service';
import { TableElement } from '../model/table.data';
import { elementAt, forkJoin } from 'rxjs';
import {MatSort, Sort} from "@angular/material/sort";
import { MatTableDataSource } from '@angular/material/table';
import { LiveAnnouncer } from "@angular/cdk/a11y";
import {MatTreeFlatDataSource, MatTreeFlattener} from "@angular/material/tree";
import {FlatTreeControl} from "@angular/cdk/tree";
import { BasicCertificateDTO } from '../model/basicCertificate.dto';
import { MatDialog } from '@angular/material/dialog';
import { FormDialogComponent } from '../form-dialog/form-dialog.component';
import { DetailsDialogComponent } from '../details-dialog/details-dialog.component';
import {environment} from "../../../../env/env";

export interface CertificateTreeNode {
  certificate: BasicCertificateDTO;
  children: CertificateTreeNode[];
}

interface ExampleFlatNode {
  expandable: boolean;
  name: any;
  level: number;
}

@Component({
  selector: 'app-certificate-manager',
  templateUrl: './certificate-manager.component.html',
  styleUrls: ['./certificate-manager.component.css']
})
export class CertificateManagerComponent implements OnInit {
  displayedColumns: string[] = ['name', "app", 'email', 'status', 'reject'];
  dataSource: MatTableDataSource<TableElement>;
  currentRowClick: any = null;
  validateMap : any = {};
  private _transformer = (node: CertificateTreeNode, level: number) => {
    return {
      expandable: !!node.children && node.children.length > 0,
      name: node,
      level: level,
    };
  };


  treeControl = new FlatTreeControl<ExampleFlatNode>(
    node => node.level,
    node => node.expandable,
  );

  treeFlattener = new MatTreeFlattener(
    this._transformer,
    node => node.level,
    node => node.expandable,
    node => node.children,
  );
  dataSource1 = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

  hasChild = (_: number, node: ExampleFlatNode) => node.expandable;

  constructor(
    private certificateService: CertificateService,
    private _liveAnnouncer: LiveAnnouncer,
    public dialog: MatDialog
  ) {
    this.dataSource = new MatTableDataSource<TableElement>();
  }

  @ViewChild(MatSort) sort: MatSort;

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  ngOnInit(): void {
    this.loadRequests();
    this.loadCertificates();
  }

  loadCertificates(){
    this.certificateService.getAllCertificates().subscribe({
      next: (data) => {
        this.dataSource1.data = this.createCertificateTree(data);
        data.forEach((cert, index)=> {
          this.verifyCertificate(cert.subjectCertificateAlias);
        })
      }
    })
  }

  verifyCertificate(alias: string) : void {
    if (alias === environment.rootAlias){
      alias = "root";
    }
    this.certificateService.verifyCertificate(alias).subscribe({
      next: (data) => {
        if(alias === "root"){
          alias = environment.rootAlias;
        }
        this.validateMap[alias] = data;
      }
    })
  }

  loadRequests(){
    this.dataSource.sort = this.sort;
    this.certificateService.getCertificateRequests().subscribe({
      next: (certificates) => {
        let observables = certificates.map(element =>
          this.certificateService.getUserDTO(element.userId)
        );

        forkJoin(observables).subscribe(users => {
          let data: TableElement[] = [];

          certificates.forEach((element, index) => {
            let user = users[index];
            let tableElement: TableElement = {
              id: element.id,
              name: user.firstName + " " + user.lastName,
              email: user.email,
              app: element.appName,
              status: element.status
            };
            data.push(tableElement);
          });

          this.dataSource.data = data;
        });
      }
    });
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }

  rowClick(row: any){
    if (this.currentRowClick == row)
      this.currentRowClick = null;
    else if (row.status == "PENDING")
      this.currentRowClick = row;
  }

  reject(element : any){
    this.certificateService.rejectCertificateRequest(element.id).subscribe({
      next: (data) => {
        this.dataSource.data.forEach((row, index) => {
          if (row.id === element.id) {
            row.status = "REJECTED"
            this.currentRowClick = null;
          }
        });
      }
    });
  }

  findRootCertificate(certificates: BasicCertificateDTO[]): BasicCertificateDTO | undefined {
    return certificates.find(cert => cert.issuerEmail === cert.subject.email);
  }

  buildCertificateTree(rootCertificate: BasicCertificateDTO, allCertificates: BasicCertificateDTO[]): CertificateTreeNode {
    const rootNode: CertificateTreeNode = {
      certificate: rootCertificate,
      children: []
    };

    function attachChildren(node: CertificateTreeNode) {
      allCertificates.forEach(cert => {
        if (cert.issuerEmail === node.certificate.subject.email) {
          const childNode: CertificateTreeNode = {
            certificate: cert,
            children: []
          };
          node.children.push(childNode);
          attachChildren(childNode);
        }
      });
    }

    attachChildren(rootNode);
    return rootNode;
  }

  createCertificateTree(certificates: BasicCertificateDTO[]): CertificateTreeNode[] {
    const rootCert = this.findRootCertificate(certificates);
    certificates = certificates.filter(element => element !== rootCert);
    if (!rootCert) {
      console.error("No root certificate found.");
      return [];
    }
    return [this.buildCertificateTree(rootCert, certificates)];
  }

  extensionsContainsCA(node : any){
    if(node.certificate.extensions.includes('CA')){
      return true;
    }
    return false;
  }

  createCertificate(node: any) {
    const dialogRef = this.dialog.open(FormDialogComponent, {
      data: { node: node, request: this.currentRowClick}
    });
    dialogRef.afterClosed().subscribe((result) => {
      if(result !== undefined) {
        this.loadCertificates();
        this.loadRequests();
      }
    });
  }

  showDetails(node: any) {
    const dialogRef = this.dialog.open(DetailsDialogComponent, {
      data: { node: node, request: this.currentRowClick}
    });
    dialogRef.afterClosed().subscribe((result) => {
      this.loadCertificates();
    });
  }
}
