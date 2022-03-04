import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IgrscmsTestModule } from '../../../test.module';
import { NoticeBoardUpdateComponent } from 'app/entities/notice-board/notice-board-update.component';
import { NoticeBoardService } from 'app/entities/notice-board/notice-board.service';
import { NoticeBoard } from 'app/shared/model/notice-board.model';

describe('Component Tests', () => {
  describe('NoticeBoard Management Update Component', () => {
    let comp: NoticeBoardUpdateComponent;
    let fixture: ComponentFixture<NoticeBoardUpdateComponent>;
    let service: NoticeBoardService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgrscmsTestModule],
        declarations: [NoticeBoardUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(NoticeBoardUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NoticeBoardUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NoticeBoardService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NoticeBoard(123);
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
        const entity = new NoticeBoard();
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
