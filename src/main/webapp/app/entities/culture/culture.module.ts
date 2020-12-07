import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FanpageSharedModule } from 'app/shared/shared.module';
import { CultureComponent } from './culture.component';
import { CultureDetailComponent } from './culture-detail.component';
import { CultureUpdateComponent } from './culture-update.component';
import { CultureDeleteDialogComponent } from './culture-delete-dialog.component';
import { cultureRoute } from './culture.route';
import { AncientCultureComponent } from 'app/entities/culture/era/ancient-culture.component';
import { ClassicalCultureComponent } from 'app/entities/culture/era/classical-culture.component';

@NgModule({
  imports: [FanpageSharedModule, RouterModule.forChild(cultureRoute)],
  declarations: [
    CultureComponent,
    CultureDetailComponent,
    CultureUpdateComponent,
    CultureDeleteDialogComponent,
    AncientCultureComponent,
    ClassicalCultureComponent,
  ],
  entryComponents: [CultureDeleteDialogComponent],
})
export class FanpageCultureModule {}
