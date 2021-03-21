import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'election',
        loadChildren: () => import('./election/election.module').then(m => m.ElectoralSystemElectionModule),
      },
      {
        path: 'figure',
        loadChildren: () => import('./figure/figure.module').then(m => m.ElectoralSystemFigureModule),
      },
      {
        path: 'candidate',
        loadChildren: () => import('./candidate/candidate.module').then(m => m.ElectoralSystemCandidateModule),
      },
      {
        path: 'vote',
        loadChildren: () => import('./vote/vote.module').then(m => m.ElectoralSystemVoteModule),
      },
      {
        path: 'user-app',
        loadChildren: () => import('./user-app/user-app.module').then(m => m.ElectoralSystemUserAppModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ElectoralSystemEntityModule {}
