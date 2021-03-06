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
import { MedievalCultureComponent } from './era/medieval-culture.component';
import { EarlyModernCultureComponent } from './era/early-modern-culture.component';
import { IndustrialCultureComponent } from './era/industrial-culture.component';
import { ContemporaryCultureComponent } from './era/contemporary-culture.component';

@NgModule({
  imports: [FanpageSharedModule, RouterModule.forChild(cultureRoute)],
  declarations: [
    CultureComponent,
    CultureDetailComponent,
    CultureUpdateComponent,
    CultureDeleteDialogComponent,
    AncientCultureComponent,
    ClassicalCultureComponent,
    MedievalCultureComponent,
    EarlyModernCultureComponent,
    IndustrialCultureComponent,
    ContemporaryCultureComponent,
  ],
  entryComponents: [CultureDeleteDialogComponent],
})
export class FanpageCultureModule {}
