import { Component, OnInit } from '@angular/core';
import { CertificateService } from '../certificate.service';
import { ReviewService } from '../../review/review.service';
import { TableElement } from '../model/table.data';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-certificate-manager',
  templateUrl: './certificate-manager.component.html',
  styleUrl: './certificate-manager.component.css'
})
export class CertificateManagerComponent implements OnInit {
  displayedColumns: string[] = ['name', 'email', 'status'];
  dataSource: TableElement[] = [];

  constructor(private certificateService: CertificateService) { }

  ngOnInit(): void {
    this.certificateService.getCertificateRequests().subscribe({
      next: (certificates) => {
        
        let observables = certificates.map(element =>
          this.certificateService.getUserDTO(element.userId)
        );
    
        forkJoin(observables).subscribe(users => {
          let data: TableElement[] = [];
    
          certificates.forEach((element, index) => {
            let user = users[index];
            console.log(user);
            let tableElement: TableElement = {
              name: user.firstName + " " + user.lastName,
              email: user.email,
              status: element.status
            };
            data.push(tableElement);
          });
    
          this.dataSource = data;
        });
      }
    });
  }
}
