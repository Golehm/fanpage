import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICulture, Culture } from 'app/shared/model/culture.model';
import { CultureService } from './culture.service';
import { CultureComponent } from './culture.component';
import { CultureDetailComponent } from './culture-detail.component';
import { CultureUpdateComponent } from './culture-update.component';
import { AncientCultureComponent } from 'app/entities/culture/era/ancient-culture.component';
import { ClassicalCultureComponent } from 'app/entities/culture/era/classical-culture.component';
import { MedievalCultureComponent } from './era/medieval-culture.component';
import { EarlyModernCultureComponent } from './era/early-modern-culture.component';
import { IndustrialCultureComponent } from './era/industrial-culture.component';
import { ContemporaryCultureComponent } from './era/contemporary-culture.component';

@Injectable({ providedIn: 'root' })
export class CultureResolve implements Resolve<ICulture> {
  constructor(private service: CultureService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICulture> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((culture: HttpResponse<Culture>) => {
          if (culture.body) {
            return of(culture.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Culture());
  }
}

export const cultureRoute: Routes = [
  {
    path: '',
    component: CultureComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Cultures',
    },
  },
  {
    path: ':id/view',
    component: CultureDetailComponent,
    resolve: {
      culture: CultureResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Cultures',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CultureUpdateComponent,
    resolve: {
      culture: CultureResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Cultures',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CultureUpdateComponent,
    resolve: {
      culture: CultureResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Cultures',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/:name',
    component: CultureDetailComponent,
    resolve: {
      culture: CultureResolve,
    },
    data: {
      pageTitle: 'Cultures',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'ancient',
    component: AncientCultureComponent,
    resolve: {
      culture: CultureResolve,
    },
    data: {
      pageTitle: 'Cultures',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'classical',
    component: ClassicalCultureComponent,
    resolve: {
      culture: CultureResolve,
    },
    data: {
      pageTitle: 'Cultures',
    },
  },
  {
    path: 'medieval',
    component: MedievalCultureComponent,
    resolve: {
      culture: CultureResolve,
    },
    data: {
      authorities: [],
      pageTitle: 'Cultures',
    },
  },
  {
    path: 'early-modern',
    component: EarlyModernCultureComponent,
    resolve: {
      culture: CultureResolve,
    },
    data: {
      authorities: [],
      pageTitle: 'Cultures',
    },
  },
  {
    path: 'industrial',
    component: IndustrialCultureComponent,
    resolve: {
      culture: CultureResolve,
    },
    data: {
      authorities: [],
      pageTitle: 'Cultures',
    },
  },
  {
    path: 'contemporary',
    component: ContemporaryCultureComponent,
    resolve: {
      culture: CultureResolve,
    },
    data: {
      authorities: [],
      pageTitle: 'Cultures',
    },
  },
];
