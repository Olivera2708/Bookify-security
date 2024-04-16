import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MapComponent } from './map/map.component';
import {MaterialModule} from "../infrastructure/material/material.module";



@NgModule({
  declarations: [
    MapComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports:[
    MapComponent,
  ]
})
export class SharedModule { }
