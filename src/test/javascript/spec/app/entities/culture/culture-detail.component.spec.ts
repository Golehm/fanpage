import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { FanpageTestModule } from '../../../test.module';
import { CultureDetailComponent } from 'app/entities/culture/culture-detail.component';
import { Culture } from 'app/shared/model/culture.model';

describe('Component Tests', () => {
  describe('Culture Management Detail Component', () => {
    let comp: CultureDetailComponent;
    let fixture: ComponentFixture<CultureDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ culture: new Culture(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FanpageTestModule],
        declarations: [CultureDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CultureDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CultureDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load culture on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.culture).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
