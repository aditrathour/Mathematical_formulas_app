import React, { useEffect } from 'react';
import { SafeAreaProvider } from 'react-native-safe-area-context';
import { AppNavigator } from './src/navigation/AppNavigator';
import { databaseService } from './src/services/DatabaseService';

export default function App() {
  useEffect(() => {
    // Initialize database when app starts
    databaseService.initDatabase();
  }, []);

  return (
    <SafeAreaProvider>
      <AppNavigator />
    </SafeAreaProvider>
  );
}
