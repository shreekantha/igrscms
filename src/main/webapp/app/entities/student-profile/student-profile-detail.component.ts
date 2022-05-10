import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IStudentProfile } from 'app/shared/model/student-profile.model';

@Component({
  selector: 'jhi-student-profile-detail',
  templateUrl: './student-profile-detail.component.html',
})
export class StudentProfileDetailComponent implements OnInit {
  studentProfile: IStudentProfile | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ studentProfile }) => (this.studentProfile = studentProfile));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
