import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { VERSION } from 'app/app.constants';
import { AccountService } from 'app/core/auth/account.service';
import { LANGUAGES } from 'app/core/language/language.constants';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { LoginService } from 'app/core/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { JhiLanguageService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['navbar.scss'],
})
export class NavbarComponent implements OnInit {
  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  swaggerEnabled?: boolean;
  version: string;
  basic: any[] = [
    { link: 'institute', label: 'Institute', icon: 'asterisk' },
    { link: 'home-img', label: 'Home Img', icon: 'asterisk' },
    { link: 'notice-board', label: 'Notice Board', icon: 'asterisk' },
    { divider: true, icon: 'asterisk' },
    { link: 'gallery-cat', label: 'Gallery Cat', icon: 'asterisk' },
    { link: 'gallery', label: 'Gallery', icon: 'asterisk' },
    { divider: true, icon: 'asterisk' },
    { link: 'testimonial', label: 'Testimonial', icon: 'asterisk' },
    { link: 'about-us', label: 'About Us', icon: 'asterisk' },
    { link: 'Vision And Mission', label: 'Vision And Mission', icon: 'asterisk' },
    { divider: true, icon: 'asterisk' },
    { link: 'news', label: 'News', icon: 'asterisk' },
    { link: 'contact-details', label: 'Contact Details', icon: 'asterisk' },
    { divider: true, icon: 'asterisk' },
    { link: 'speaker-desk', label: 'Speaker Desk', icon: 'asterisk' },
  ];

  users: any[] = [
    { link: 'user-profile', label: 'Staff Profile', icon: 'asterisk' },
    { link: 'student-profile', label: 'Student Profile', icon: 'asterisk' },
    { link: 'alumni-profile', label: 'Alumni Profile', icon: 'asterisk' },

    // { divider: true, icon: 'asterisk' },
  ];
  navItems: any[] = [
    { link: 'course', label: 'Course', icon: 'asterisk' },
    { divider: true, icon: 'asterisk' },
    { link: 'term', label: 'Term', icon: 'asterisk' },
    { divider: true, icon: 'asterisk' },
    { link: 'academic-calendar', label: 'Academic Calendar', icon: 'asterisk' },
    { divider: true, icon: 'asterisk' },
    { link: 'class-time-table-config', label: 'Class Time Table Config', icon: 'asterisk' },
    { link: 'class-time-table', label: 'Class Time Table', icon: 'asterisk' },
  ];

  constructor(
    private loginService: LoginService,
    private languageService: JhiLanguageService,
    private sessionStorage: SessionStorageService,
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private profileService: ProfileService,
    private router: Router
  ) {
    this.version = VERSION ? (VERSION.toLowerCase().startsWith('v') ? VERSION : 'v' + VERSION) : '';
  }

  ngOnInit(): void {
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.swaggerEnabled = profileInfo.swaggerEnabled;
    });
  }

  changeLanguage(languageKey: string): void {
    this.sessionStorage.store('locale', languageKey);
    this.languageService.changeLanguage(languageKey);
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  getImageUrl(): string {
    return this.isAuthenticated() ? this.accountService.getImageUrl() : '';
  }
}
