import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElectoralSystemSharedModule } from 'app/shared/shared.module';
import { UserAppComponent } from './user-app.component';
import { UserAppDetailComponent } from './user-app-detail.component';
import { UserAppUpdateComponent } from './user-app-update.component';
import { UserAppDeleteDialogComponent } from './user-app-delete-dialog.component';
import { userAppRoute } from './user-app.route';

@NgModule({
  imports: [ElectoralSystemSharedModule, RouterModule.forChild(userAppRoute)],
  declarations: [UserAppComponent, UserAppDetailComponent, UserAppUpdateComponent, UserAppDeleteDialogComponent],
  entryComponents: [UserAppDeleteDialogComponent],
})
export class ElectoralSystemUserAppModule {}
