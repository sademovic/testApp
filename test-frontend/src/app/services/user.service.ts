import { Injectable, EventEmitter } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  baseUrl = 'http://localhost:8081/';

  loginHeaders = {};

  loginFailed = new EventEmitter();
  loginSuccess = new EventEmitter<any>();
  registerSuccess = new EventEmitter();

  constructor(private http: HttpClient) {}

  getUsers = () => {
    return this.http.get(`${this.baseUrl}/user/all`);
  };

  createUser = (user) => {
    return this.http.post(`${this.baseUrl}/user/register`, user);
  };

  removeUser = (userId) => {
    return this.http.delete(`${this.baseUrl}/user/${userId}`);
  };
}
