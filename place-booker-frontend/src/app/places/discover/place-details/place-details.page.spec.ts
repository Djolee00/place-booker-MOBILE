import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PlaceDetailsPage } from './place-details.page';

describe('PlaceDetailsPage', () => {
  let component: PlaceDetailsPage;
  let fixture: ComponentFixture<PlaceDetailsPage>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(PlaceDetailsPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
