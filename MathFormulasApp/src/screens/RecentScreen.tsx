import React from 'react';
import {
  View,
  Text,
  StyleSheet,
} from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { theme } from '../theme';

export const RecentScreen: React.FC = () => {
  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.content}>
        <Text style={styles.title}>Recent</Text>
        <Text style={styles.subtitle}>Recently viewed formulas will appear here</Text>
      </View>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: theme.colors.surface[1],
  },
  content: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: theme.spacing.lg,
  },
  title: {
    ...theme.typography.headlineLarge,
    color: theme.colors.surface[10],
    marginBottom: theme.spacing.md,
  },
  subtitle: {
    ...theme.typography.bodyLarge,
    color: theme.colors.surface[7],
    textAlign: 'center',
  },
});