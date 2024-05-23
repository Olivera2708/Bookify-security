import {Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { DomSanitizer } from "@angular/platform-browser"

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  title = 'Bookify';
  constructor(private router: Router) { }

  ngOnInit(): void {
  }

}
