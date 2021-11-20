import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appStandout]',
})
export class StandoutDirective {
  constructor(private el: ElementRef) {}
  @HostListener('mouseenter') onMouseEnter() {
    this.highlight();
  }
  @HostListener('mouseleave') onMouseLeave() {
    this.noHighlight();
  }

  private highlight() {
    this.el.nativeElement.style.transform = 'scale(1.1)';
    this.el.nativeElement.style.transitionDuration = '0.3s';
    this.el.nativeElement.style.transitionProperty = 'transform';
    this.el.nativeElement.style.color = 'grey';
  }
  private noHighlight() {
    this.el.nativeElement.style.transform = '';
    this.el.nativeElement.style.transitionDuration = '';
    this.el.nativeElement.style.transitionProperty = '';
    this.el.nativeElement.style.color = 'black';
  }
}
