import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElectoralSystemSharedModule } from 'app/shared/shared.module';
import { FigureComponent } from './figure.component';
import { FigureDetailComponent } from './figure-detail.component';
import { FigureUpdateComponent } from './figure-update.component';
import { FigureDeleteDialogComponent } from './figure-delete-dialog.component';
import { figureRoute } from './figure.route';

@NgModule({
  imports: [ElectoralSystemSharedModule, RouterModule.forChild(figureRoute)],
  declarations: [FigureComponent, FigureDetailComponent, FigureUpdateComponent, FigureDeleteDialogComponent],
  entryComponents: [FigureDeleteDialogComponent],
})
export class ElectoralSystemFigureModule {}
