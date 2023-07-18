import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PlacesPage } from './places.page';

describe('PlacesPage', () => {
  let component: PlacesPage;
  let fixture: ComponentFixture<PlacesPage>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(PlacesPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
