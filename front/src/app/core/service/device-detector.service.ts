import { Injectable } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';

@Injectable({
  providedIn: 'root'
})
export class DeviceDetectorService {
  constructor(private breakpointObserver: BreakpointObserver) {}

  isSmallDevice(): boolean {
    return this.breakpointObserver.isMatched(Breakpoints.Handset);
  }
}
