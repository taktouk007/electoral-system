import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ElectoralSystemTestModule } from '../../../test.module';
import { UserAppDetailComponent } from 'app/entities/user-app/user-app-detail.component';
import { UserApp } from 'app/shared/model/user-app.model';

describe('Component Tests', () => {
  describe('UserApp Management Detail Component', () => {
    let comp: UserAppDetailComponent;
    let fixture: ComponentFixture<UserAppDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ userApp: new UserApp(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElectoralSystemTestModule],
        declarations: [UserAppDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserAppDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserAppDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load userApp on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userApp).toEqual(jasmine.objectContaining({ id: 123 }));
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
