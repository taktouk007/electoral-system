import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElectoralSystemSharedModule } from 'app/shared/shared.module';
import { VoteComponent } from './vote.component';
import { VoteDetailComponent } from './vote-detail.component';
import { VoteUpdateComponent } from './vote-update.component';
import { VoteDeleteDialogComponent } from './vote-delete-dialog.component';
import { voteRoute } from './vote.route';

@NgModule({
  imports: [ElectoralSystemSharedModule, RouterModule.forChild(voteRoute)],
  declarations: [VoteComponent, VoteDetailComponent, VoteUpdateComponent, VoteDeleteDialogComponent],
  entryComponents: [VoteDeleteDialogComponent],
})
export class ElectoralSystemVoteModule {}
