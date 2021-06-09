import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';

import { Account } from 'app/core/user/account.model';
import { IFigure } from 'app/shared/model/figure.model';
import { FigureService } from 'app/entities/figure/figure.service';
import { VoteService } from 'app/entities/vote/vote.service';
import { HttpResponse } from '@angular/common/http';
import { IVote } from 'app/shared/model/vote.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import * as moment from 'moment';
@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  authSubscription?: Subscription;
  figures?: IFigure[];
  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private figureService: FigureService,
    private voteService: VoteService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
  }

  loadAll(): void {
    this.figureService.query().subscribe((res: HttpResponse<IFigure[]>) => (this.figures = res.body || []));
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  citoyenVote(figure: IFigure) {
    console.log('citoyenVote');
    let vote: IVote = {};
    /*  candidateId: Number(figure.candidateId),
      submissionDate: moment.now(),
      electionId: Math.random()
    }; */

    this.voteService.create(vote).subscribe(res => {
      let user: IUser = {};
      //user.voteLocked = true;
      //user = this.userService.find();
      this.userService.update(user).subscribe(userUpdate => {
        console.log('user locked succefully');
      });
    });
  }

  login(): void {
    this.loginModalService.open();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }
}
