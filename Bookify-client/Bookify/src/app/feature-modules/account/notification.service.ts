import {Injectable} from '@angular/core';
import {environment} from "../../../env/env";

import * as Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import {Message} from "./model/message";
import {MatSnackBar} from "@angular/material/snack-bar";
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Settings} from "./model/settings";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private serverUrl: string = environment.apiSocket;
  private stompClient: Stomp.Client;

  notificationNumber$: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  notificationNum: Observable<number> = this.notificationNumber$.asObservable();

  isLoaded: boolean = false;
  isSocketOpen: boolean = false;
  messages: Message[] = []

  constructor(private snackBar: MatSnackBar,
              private httpClient: HttpClient) {
  }


  initializeWebSocketConnection(userId: number | undefined): void {
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    let that = this;
    this.stompClient.connect({}, function () {
      that.isLoaded = true;
      that.openSocket(userId);
    });
  }

  closeSocket(): void {
    if (this.isSocketOpen) {
      this.isSocketOpen = false;
      this.stompClient.disconnect(() => {
      }, {});
    }
  }

  openSocket(userId: number | undefined) {
    if (this.isLoaded) {
      this.isSocketOpen = true;
      this.stompClient.subscribe("/socket-publisher/" + userId, (message: {
        body: string;
      }) => {
        this.handleResult(message);
      });
    }
  }

  handleResult(message: {
    body: string;
  }) {
    if (message.body) {
      let messageResult = JSON.parse(message.body);
      if (messageResult['description']) {
        this.snackBar.open(messageResult['description'], 'OK', {
          duration: 3000, horizontalPosition: 'end', verticalPosition: 'bottom'
        });
        this.messages.push(messageResult);
        this.notificationNumber$.next(this.messages.length);
      }
    }
  }

  getAllNotifications(userId: number): Observable<Message[]> {
    return this.httpClient.get<Message[]>(environment.apiHost + "notifications/" + userId);
  }

  getNotificationSettings(userId: number): Observable<Settings> {
    return this.httpClient.get<Settings>(environment.apiHost + "notifications/" + userId + "/settings");
  }

  setNotificationSettings(userId: number, updatedSettings: Settings): Observable<Settings> {
    return this.httpClient.put<Settings>(environment.apiHost + "notifications/" + userId + "/settings", updatedSettings);
  }
}
