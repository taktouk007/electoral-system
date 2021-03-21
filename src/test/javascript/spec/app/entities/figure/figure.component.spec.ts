import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ElectoralSystemTestModule } from '../../../test.module';
import { FigureComponent } from 'app/entities/figure/figure.component';
import { FigureService } from 'app/entities/figure/figure.service';
import { Figure } from 'app/shared/model/figure.model';

describe('Component Tests', () => {
  describe('Figure Management Component', () => {
    let comp: FigureComponent;
    let fixture: ComponentFixture<FigureComponent>;
    let service: FigureService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElectoralSystemTestModule],
        declarations: [FigureComponent],
      })
        .overrideTemplate(FigureComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FigureComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FigureService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Figure(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.figures && comp.figures[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
