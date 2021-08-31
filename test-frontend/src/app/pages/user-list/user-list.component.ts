import { Component, OnDestroy, OnInit } from '@angular/core';
import { of, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
})
export class UserListComponent implements OnInit, OnDestroy {
  users$: any = of([]);
  lifetimeSubject: Subject<boolean> = new Subject<boolean>();

  deletedUserId: number;
  timeoutAlert: any;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.users$ = this.userService.getUsers();
  }

  onDeleteUser = (user) => {
    this.userService
      .removeUser(user.id)
      .pipe(takeUntil(this.lifetimeSubject))
      .subscribe(() => {
        this.deletedUserId = user.id;
        this.users$ = this.userService.getUsers();
        setTimeout(() => {
          this.deletedUserId = null;
        }, 2500);
      });
  };

  ngOnDestroy(): void {
    this.lifetimeSubject.next(true);
    this.lifetimeSubject.complete();
    clearTimeout(this.timeoutAlert);
  }
}
