import { Injectable, effect, inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private platformId = inject(PLATFORM_ID);
  
  constructor() {
    // Effect to apply dark theme on initialization
    effect(() => {
      this.applyDarkTheme();
    });
  }
  
  /**
   * Apply dark theme to DOM
   */
  private applyDarkTheme(): void {
    if (!isPlatformBrowser(this.platformId)) {
      return;
    }
    
    const html = document.documentElement;
    html.classList.add('dark');
  }
}
