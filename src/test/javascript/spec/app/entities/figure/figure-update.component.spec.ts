import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ElectoralSystemTestModule } from '../../../test.module';
import { FigureUpdateComponent } from 'app/entities/figure/figure-update.component';
import { FigureService } from 'app/entities/figure/figure.service';
import { Figure } from 'app/shared/model/figure.model';

describe('Component Tests', () => {
  describe('Figure Management Update Component', () => {
    let comp: FigureUpdateComponent;
    let fixture: ComponentFixture<FigureUpdateComponent>;
    let service: FigureService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElectoralSystemTestModule],
        declarations: [FigureUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FigureUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FigureUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FigureService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Figure(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Figure();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
