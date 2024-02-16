import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.placebooker',
  appName: 'place-booker-frontend',
  webDir: 'www',
  server: {
    allowNavigation: [],
  },
  android: {
    allowMixedContent: true,
  },
};

export default config;
