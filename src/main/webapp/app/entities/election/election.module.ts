import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElectoralSystemSharedModule } from 'app/shared/shared.module';
import { ElectionComponent } from './election.component';
import { ElectionDetailComponent } from './election-detail.component';
import { ElectionUpdateComponent } from './election-update.component';
import { ElectionDeleteDialogComponent } from './election-delete-dialog.component';
import { electionRoute } from './election.route';

@NgModule({
  imports: [ElectoralSystemSharedModule, RouterModule.forChild(electionRoute)],
  declarations: [ElectionComponent, ElectionDetailComponent, ElectionUpdateComponent, ElectionDeleteDialogComponent],
  entryComponents: [ElectionDeleteDialogComponent],
})
export class ElectoralSystemElectionModule {}
