import { Component, OnInit, ViewChild } from '@angular/core';
import { CertificateService } from '../certificate.service';
import { TableElement } from '../model/table.data';
import { forkJoin } from 'rxjs';
import {MatSort, Sort} from "@angular/material/sort";
import { MatTableDataSource } from '@angular/material/table';
import { LiveAnnouncer } from "@angular/cdk/a11y";
import {MatTreeFlatDataSource, MatTreeFlattener} from "@angular/material/tree";
import {FlatTreeControl} from "@angular/cdk/tree";


interface FoodNode {
  name: string;
  children?: FoodNode[];
}

const TREE_DATA: FoodNode[] = [
  {
    name: 'Fruit',
    children: [{name: 'Apple'}, {name: 'Banana'}, {name: 'Fruit loops'}],
  },
  {
    name: 'Vegetables',
    children: [
      {
        name: 'Green',
        children: [{name: 'Broccoli'}, {name: 'Brussels sprouts'}],
      },
      {
        name: 'Orange',
        children: [{name: 'Pumpkins', children: [{name: 'Broccoli', children: [{name: 'Broccoli'}, {name: 'Brussels sprouts', children: [{name: 'Broccoli'}, {name: 'Brussels sprouts', children: [{name: 'Broccoli', children: [{name: 'Broccoli', children: [{name: 'Broccoli', children: [{name: 'Broccoli', children: [{name: 'Broccoli', children: [{name: 'Broccoli', children: [{name: 'Broccoli', children: [{name: 'Broccoli', children: [{name: 'Broccoli', children: [{name: 'Broccoli', children: [{name: 'Broccoli'}, {name: 'Brussels sprouts'}],}, {name: 'Brussels sprouts'}],}, {name: 'Brussels sprouts'}],}, {name: 'Brussels sprouts'}],}, {name: 'Brussels sprouts'}],}, {name: 'Brussels sprouts'}],}, {name: 'Brussels sprouts'}],}, {name: 'Brussels sprouts'}],}, {name: 'Brussels sprouts'}],}, {name: 'Brussels sprouts'}],}, {name: 'Brussels sprouts'}],}],}],}, {name: 'Brussels sprouts'}],}, {name: 'Carrots'}],
      },
    ],
  },
];

/** Flat node with expandable and level information */
interface ExampleFlatNode {
  expandable: boolean;
  name: string;
  level: number;
}




@Component({
  selector: 'app-certificate-manager',
  templateUrl: './certificate-manager.component.html',
  styleUrls: ['./certificate-manager.component.css']
})
export class CertificateManagerComponent implements OnInit {
  displayedColumns: string[] = ['name', 'email', 'status', 'reject'];
  dataSource: MatTableDataSource<TableElement>;
  currentRowClick: any = null;
  //////////////////////
  private _transformer = (node: FoodNode, level: number) => {
    return {
      expandable: !!node.children && node.children.length > 0,
      name: node.name,
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
  ////////////////////

  constructor(
    private certificateService: CertificateService,
    private _liveAnnouncer: LiveAnnouncer
  ) {
    this.dataSource = new MatTableDataSource<TableElement>();
    this.dataSource1.data = TREE_DATA;
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
        console.log(data);
      }
    })
  }

  loadRequests(){
    this.dataSource.sort = this.sort; // Bind MatSort to MatTableDataSource
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
              status: element.status
            };
            data.push(tableElement);
          });

          this.dataSource.data = data; // Update MatTableDataSource with new data
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
    else
      this.currentRowClick = row;
  }

  reject(element : any){
    this.certificateService.rejectCertificateRequest(element.id).subscribe({
      next: (data) => {
        this.dataSource.data.forEach((row, index) => {
          if (row.id === element.id)
            row.status = "REJECTED"
        });
      }
    });
  }
}
