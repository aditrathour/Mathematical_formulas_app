import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Share,
} from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { Ionicons } from '@expo/vector-icons';
import { theme } from '../theme';
import { Card } from '../components/Card';
import { Button } from '../components/Button';
import { Formula } from '../types';

interface FormulaDetailScreenProps {
  navigation: any;
  route: {
    params: {
      formula: Formula;
    };
  };
}

export const FormulaDetailScreen: React.FC<FormulaDetailScreenProps> = ({ navigation, route }) => {
  const { formula } = route.params;
  const [isFavorite, setIsFavorite] = useState(false);

  const handleShare = async () => {
    try {
      await Share.share({
        message: `${formula.name}\n\nFormula: ${formula.formula}\n\nDescription: ${formula.description}`,
        title: formula.name,
      });
    } catch (error) {
      console.error('Error sharing:', error);
    }
  };

  const toggleFavorite = () => {
    setIsFavorite(!isFavorite);
    // TODO: Implement actual favorite functionality with local storage
  };

  const getDifficultyColor = (difficulty: string) => {
    switch (difficulty) {
      case 'beginner': return theme.colors.success[500];
      case 'intermediate': return theme.colors.warning[500];
      case 'advanced': return theme.colors.error[500];
      default: return theme.colors.surface[5];
    }
  };

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity
          style={styles.backButton}
          onPress={() => navigation.goBack()}
        >
          <Ionicons name="arrow-back" size={24} color={theme.colors.surface[10]} />
        </TouchableOpacity>
        <View style={styles.headerActions}>
          <TouchableOpacity
            style={styles.actionButton}
            onPress={toggleFavorite}
          >
            <Ionicons
              name={isFavorite ? 'heart' : 'heart-outline'}
              size={24}
              color={isFavorite ? theme.colors.error[500] : theme.colors.surface[8]}
            />
          </TouchableOpacity>
          <TouchableOpacity
            style={styles.actionButton}
            onPress={handleShare}
          >
            <Ionicons name="share-outline" size={24} color={theme.colors.surface[8]} />
          </TouchableOpacity>
        </View>
      </View>

      <ScrollView style={styles.content} showsVerticalScrollIndicator={false}>
        <Card style={styles.formulaCard} elevation={3}>
          <View style={styles.formulaHeader}>
            <Text style={styles.formulaName}>{formula.name}</Text>
            <View style={[styles.difficultyBadge, { backgroundColor: getDifficultyColor(formula.difficulty) }]}>
              <Text style={styles.difficultyText}>{formula.difficulty}</Text>
            </View>
          </View>

          <View style={styles.formulaContainer}>
            <Text style={styles.formulaLabel}>Formula</Text>
            <Text style={styles.formulaText}>{formula.formula}</Text>
          </View>

          <View style={styles.descriptionContainer}>
            <Text style={styles.descriptionLabel}>Description</Text>
            <Text style={styles.descriptionText}>{formula.description}</Text>
          </View>

          {formula.subcategory && (
            <View style={styles.subcategoryContainer}>
              <Text style={styles.subcategoryLabel}>Subcategory</Text>
              <View style={styles.subcategoryBadge}>
                <Text style={styles.subcategoryText}>{formula.subcategory}</Text>
              </View>
            </View>
          )}

          <View style={styles.tagsContainer}>
            <Text style={styles.tagsLabel}>Tags</Text>
            <View style={styles.tags}>
              {formula.tags.map((tag, index) => (
                <View key={index} style={styles.tag}>
                  <Text style={styles.tagText}>{tag}</Text>
                </View>
              ))}
            </View>
          </View>
        </Card>

        <Card style={styles.sectionCard} elevation={2}>
          <Text style={styles.sectionTitle}>Tips & Tricks</Text>
          {formula.tips.map((tip, index) => (
            <View key={index} style={styles.tipItem}>
              <View style={styles.tipBullet}>
                <Ionicons name="bulb-outline" size={16} color={theme.colors.warning[500]} />
              </View>
              <Text style={styles.tipText}>{tip}</Text>
            </View>
          ))}
        </Card>

        <Card style={styles.sectionCard} elevation={2}>
          <Text style={styles.sectionTitle}>Examples</Text>
          {formula.examples.map((example, index) => (
            <View key={index} style={styles.exampleItem}>
              <View style={styles.exampleHeader}>
                <Text style={styles.exampleNumber}>Example {index + 1}</Text>
              </View>
              <View style={styles.exampleContent}>
                <Text style={styles.exampleLabel}>Problem:</Text>
                <Text style={styles.exampleProblem}>{example.problem}</Text>
              </View>
              <View style={styles.exampleContent}>
                <Text style={styles.exampleLabel}>Solution:</Text>
                <Text style={styles.exampleSolution}>{example.solution}</Text>
              </View>
              <View style={styles.exampleContent}>
                <Text style={styles.exampleLabel}>Explanation:</Text>
                <Text style={styles.exampleExplanation}>{example.explanation}</Text>
              </View>
            </View>
          ))}
        </Card>

        <Card style={styles.sectionCard} elevation={2}>
          <Text style={styles.sectionTitle}>Key Concepts</Text>
          {formula.concepts.map((concept, index) => (
            <View key={index} style={styles.conceptItem}>
              <View style={styles.conceptBullet}>
                <Ionicons name="checkmark-circle-outline" size={16} color={theme.colors.success[500]} />
              </View>
              <Text style={styles.conceptText}>{concept}</Text>
            </View>
          ))}
        </Card>

        <View style={styles.bottomSpacing} />
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: theme.colors.surface[1],
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: theme.spacing.lg,
    paddingBottom: theme.spacing.md,
  },
  backButton: {
    padding: theme.spacing.sm,
  },
  headerActions: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  actionButton: {
    padding: theme.spacing.sm,
    marginLeft: theme.spacing.sm,
  },
  content: {
    flex: 1,
    paddingHorizontal: theme.spacing.lg,
  },
  formulaCard: {
    marginBottom: theme.spacing.lg,
  },
  formulaHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: theme.spacing.lg,
  },
  formulaName: {
    ...theme.typography.headlineSmall,
    color: theme.colors.surface[10],
    flex: 1,
  },
  difficultyBadge: {
    paddingHorizontal: theme.spacing.md,
    paddingVertical: theme.spacing.sm,
    borderRadius: theme.borderRadius.sm,
  },
  difficultyText: {
    ...theme.typography.labelMedium,
    color: theme.colors.surface[0],
    textTransform: 'capitalize',
  },
  formulaContainer: {
    marginBottom: theme.spacing.lg,
  },
  formulaLabel: {
    ...theme.typography.labelLarge,
    color: theme.colors.surface[8],
    marginBottom: theme.spacing.sm,
  },
  formulaText: {
    ...theme.typography.headlineMedium,
    color: theme.colors.primary[500],
    fontFamily: 'monospace',
    textAlign: 'center',
    padding: theme.spacing.lg,
    backgroundColor: theme.colors.surface[2],
    borderRadius: theme.borderRadius.md,
  },
  descriptionContainer: {
    marginBottom: theme.spacing.lg,
  },
  descriptionLabel: {
    ...theme.typography.labelLarge,
    color: theme.colors.surface[8],
    marginBottom: theme.spacing.sm,
  },
  descriptionText: {
    ...theme.typography.bodyLarge,
    color: theme.colors.surface[10],
    lineHeight: 24,
  },
  subcategoryContainer: {
    marginBottom: theme.spacing.lg,
  },
  subcategoryLabel: {
    ...theme.typography.labelLarge,
    color: theme.colors.surface[8],
    marginBottom: theme.spacing.sm,
  },
  subcategoryBadge: {
    alignSelf: 'flex-start',
    backgroundColor: theme.colors.primary[100],
    paddingHorizontal: theme.spacing.md,
    paddingVertical: theme.spacing.sm,
    borderRadius: theme.borderRadius.md,
  },
  subcategoryText: {
    ...theme.typography.labelMedium,
    color: theme.colors.primary[700],
  },
  tagsContainer: {
    marginBottom: theme.spacing.md,
  },
  tagsLabel: {
    ...theme.typography.labelLarge,
    color: theme.colors.surface[8],
    marginBottom: theme.spacing.sm,
  },
  tags: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: theme.spacing.sm,
  },
  tag: {
    backgroundColor: theme.colors.surface[2],
    paddingHorizontal: theme.spacing.md,
    paddingVertical: theme.spacing.sm,
    borderRadius: theme.borderRadius.full,
  },
  tagText: {
    ...theme.typography.labelMedium,
    color: theme.colors.surface[8],
  },
  sectionCard: {
    marginBottom: theme.spacing.lg,
  },
  sectionTitle: {
    ...theme.typography.titleLarge,
    color: theme.colors.surface[10],
    marginBottom: theme.spacing.lg,
  },
  tipItem: {
    flexDirection: 'row',
    alignItems: 'flex-start',
    marginBottom: theme.spacing.md,
  },
  tipBullet: {
    marginRight: theme.spacing.sm,
    marginTop: 2,
  },
  tipText: {
    ...theme.typography.bodyMedium,
    color: theme.colors.surface[10],
    flex: 1,
    lineHeight: 22,
  },
  exampleItem: {
    marginBottom: theme.spacing.lg,
    padding: theme.spacing.md,
    backgroundColor: theme.colors.surface[2],
    borderRadius: theme.borderRadius.md,
  },
  exampleHeader: {
    marginBottom: theme.spacing.md,
  },
  exampleNumber: {
    ...theme.typography.titleSmall,
    color: theme.colors.primary[500],
    fontWeight: '600',
  },
  exampleContent: {
    marginBottom: theme.spacing.sm,
  },
  exampleLabel: {
    ...theme.typography.labelMedium,
    color: theme.colors.surface[8],
    marginBottom: theme.spacing.xs,
    fontWeight: '600',
  },
  exampleProblem: {
    ...theme.typography.bodyMedium,
    color: theme.colors.surface[10],
    fontStyle: 'italic',
  },
  exampleSolution: {
    ...theme.typography.bodyMedium,
    color: theme.colors.primary[500],
    fontFamily: 'monospace',
    backgroundColor: theme.colors.surface[0],
    padding: theme.spacing.sm,
    borderRadius: theme.borderRadius.sm,
  },
  exampleExplanation: {
    ...theme.typography.bodyMedium,
    color: theme.colors.surface[10],
  },
  conceptItem: {
    flexDirection: 'row',
    alignItems: 'flex-start',
    marginBottom: theme.spacing.md,
  },
  conceptBullet: {
    marginRight: theme.spacing.sm,
    marginTop: 2,
  },
  conceptText: {
    ...theme.typography.bodyMedium,
    color: theme.colors.surface[10],
    flex: 1,
    lineHeight: 22,
  },
  bottomSpacing: {
    height: theme.spacing.xl,
  },
});