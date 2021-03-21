import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ElectoralSystemTestModule } from '../../../test.module';
import { FigureDetailComponent } from 'app/entities/figure/figure-detail.component';
import { Figure } from 'app/shared/model/figure.model';

describe('Component Tests', () => {
  describe('Figure Management Detail Component', () => {
    let comp: FigureDetailComponent;
    let fixture: ComponentFixture<FigureDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ figure: new Figure(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElectoralSystemTestModule],
        declarations: [FigureDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FigureDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FigureDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load figure on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.figure).toEqual(jasmine.objectContaining({ id: 123 }));
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
