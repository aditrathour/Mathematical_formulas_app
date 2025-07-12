import React from 'react';
import {
  View,
  StyleSheet,
  ViewStyle,
  TouchableOpacity,
} from 'react-native';
import { theme } from '../theme';

interface CardProps {
  children: React.ReactNode;
  style?: ViewStyle;
  onPress?: () => void;
  elevation?: 1 | 2 | 3 | 4;
}

export const Card: React.FC<CardProps> = ({
  children,
  style,
  onPress,
  elevation = 1,
}) => {
  const cardStyle = [
    styles.base,
    styles[`elevation${elevation}`],
    style,
  ];

  if (onPress) {
    return (
      <TouchableOpacity
        style={cardStyle}
        onPress={onPress}
        activeOpacity={0.7}
      >
        {children}
      </TouchableOpacity>
    );
  }

  return <View style={cardStyle}>{children}</View>;
};

const styles = StyleSheet.create({
  base: {
    backgroundColor: theme.colors.surface[0],
    borderRadius: theme.borderRadius.lg,
    padding: theme.spacing.lg,
  },
  elevation1: {
    ...theme.shadows.elevation1,
  },
  elevation2: {
    ...theme.shadows.elevation2,
  },
  elevation3: {
    ...theme.shadows.elevation3,
  },
  elevation4: {
    ...theme.shadows.elevation4,
  },
});