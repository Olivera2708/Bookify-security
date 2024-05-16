import {Component, HostListener, OnInit} from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import {AuthenticationService} from "../../feature-modules/authentication/authentication.service";
import {AccountService} from "../../feature-modules/account/account.service";
import {KeycloakService} from '../../keycloak/keycloak.service';


@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrl: './navigation-bar.component.css',
})
export class NavigationBarComponent implements OnInit {
  role: string = ''
  userImage: string | ArrayBuffer | null = null;
  isScrolled: boolean = false;
  currentRoute: string = "";
  isTransparentRoute: boolean = false;
  allowedScrollRoutes: string[] = ['', ' ', '/', '/results'];
  notificationNumber: number = 0;

  constructor(private router: Router,
              private authenticationService: AuthenticationService,
              private accountService: AccountService,
              private keycloakService: KeycloakService) {
    router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.currentRoute = event.url;
        this.checkIfInitialTransparent();
      }
    });
  }

  @HostListener('window:scroll', [])
  onWindowScroll(): void {
    if (this.isTransparentRoute) {
      this.isScrolled = window.scrollY > 350;
    }
  }

  private checkIfInitialTransparent(): void {
    if (this.currentRoute.includes('/results'))
      this.isTransparentRoute = true;
    else
      this.isTransparentRoute = this.allowedScrollRoutes.includes(this.currentRoute);
    if (this.isTransparentRoute) {
      this.isScrolled = false;
      return;
    }
    this.isScrolled = true;
  }

  ngOnInit(): void {
    this.authenticationService.userState.subscribe((result: string): void => {
      const token = this.keycloakService.profile?.token;

      if(token !== undefined){
        const decodedToken = JSON.parse(atob(token.split('.')[1]));
        const roles = decodedToken.resource_access['login-app'].roles;
        console.log(roles);
        this.role = roles[0];
      }

      console.log(this.role);
      this.setAccountImageIcon();

    });
    if (this.role === '') {
      this.role = this.authenticationService.getRole();
      this.setAccountImageIcon();
    }
    this.authenticationService.getNotificationNumber().subscribe({
      next: value => this.notificationNumber = value
    });
  }

  private setAccountImageIcon(): void {
    if(this.role !== "SYSADMIN"){
      if(this.role !== ""){
        this.userImage = "assets/images/user.jpg";
        const id: number = this.authenticationService.getUserId();
        if (id === -1) return;
        this.accountService.getAccountImageId(id).subscribe({
          next: (id: number) => {
            if (id !== -1) {
              this.accountService.getAccountImage(id).subscribe({
                next: (image: Blob) => {
                  const reader: FileReader = new FileReader();
                  reader.onloadend = () => {
                    this.userImage = reader.result;
                  }
                  reader.readAsDataURL(image);
                },
                error: err => {
                  if (err.status === 404) {

                  }
                }
              });
            }
          }
        });
      }
      }
  }
  OnLogoutClick(): void {
    this.keycloakService.logout();
    // this.authenticationService.logout();
    // this.router.navigate(['']);
  }
}
