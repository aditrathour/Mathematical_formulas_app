import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  TouchableOpacity,
} from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { Ionicons } from '@expo/vector-icons';
import { theme } from '../theme';
import { Card } from '../components/Card';
import { Button } from '../components/Button';
import { formulas } from '../data/formulas';
import { Formula, Category } from '../types';

interface CategoryScreenProps {
  navigation: any;
  route: {
    params: {
      category: Category;
    };
  };
}

export const CategoryScreen: React.FC<CategoryScreenProps> = ({ navigation, route }) => {
  const { category } = route.params;
  const [selectedSubcategory, setSelectedSubcategory] = useState<string | null>(null);
  const [selectedDifficulty, setSelectedDifficulty] = useState<string | null>(null);

  const categoryFormulas = formulas.filter(formula => formula.category === category.id);
  
  const filteredFormulas = categoryFormulas.filter(formula => {
    if (selectedSubcategory && formula.subcategory !== selectedSubcategory) {
      return false;
    }
    if (selectedDifficulty && formula.difficulty !== selectedDifficulty) {
      return false;
    }
    return true;
  });

  const subcategories = Array.from(new Set(categoryFormulas.map(f => f.subcategory).filter((sub): sub is string => Boolean(sub))));
  const difficulties = Array.from(new Set(categoryFormulas.map(f => f.difficulty)));

  const renderFormulaCard = ({ item }: { item: Formula }) => (
    <Card
      style={styles.formulaCard}
      onPress={() => navigation.navigate('FormulaDetail', { formula: item })}
      elevation={2}
    >
      <View style={styles.formulaHeader}>
        <Text style={styles.formulaName}>{item.name}</Text>
        <View style={[styles.difficultyBadge, { backgroundColor: getDifficultyColor(item.difficulty) }]}>
          <Text style={styles.difficultyText}>{item.difficulty}</Text>
        </View>
      </View>
      
      <Text style={styles.formulaText}>{item.formula}</Text>
      <Text style={styles.formulaDescription} numberOfLines={2}>
        {item.description}
      </Text>
      
      {item.subcategory && (
        <View style={styles.subcategoryBadge}>
          <Text style={styles.subcategoryText}>{item.subcategory}</Text>
        </View>
      )}
      
      <View style={styles.formulaTags}>
        {item.tags.slice(0, 3).map((tag, index) => (
          <View key={index} style={styles.tag}>
            <Text style={styles.tagText}>{tag}</Text>
          </View>
        ))}
      </View>
    </Card>
  );

  const renderFilterChip = (title: string, value: string, isSelected: boolean, onPress: () => void) => (
    <TouchableOpacity
      style={[styles.filterChip, isSelected && styles.filterChipSelected]}
      onPress={onPress}
    >
      <Text style={[styles.filterChipText, isSelected && styles.filterChipTextSelected]}>
        {title}
      </Text>
    </TouchableOpacity>
  );

  const getDifficultyColor = (difficulty: string) => {
    switch (difficulty) {
      case 'beginner': return theme.colors.success[500];
      case 'intermediate': return theme.colors.warning[500];
      case 'advanced': return theme.colors.error[500];
      default: return theme.colors.surface[5];
    }
  };

  const clearFilters = () => {
    setSelectedSubcategory(null);
    setSelectedDifficulty(null);
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
        <View style={styles.headerContent}>
          <View style={[styles.categoryIcon, { backgroundColor: category.color }]}>
            <Ionicons name={category.icon as any} size={24} color="white" />
          </View>
          <View style={styles.headerText}>
            <Text style={styles.categoryTitle}>{category.name}</Text>
            <Text style={styles.categoryDescription}>{category.description}</Text>
          </View>
        </View>
      </View>

      <View style={styles.filtersContainer}>
        <View style={styles.filtersHeader}>
          <Text style={styles.filtersTitle}>Filters</Text>
          {(selectedSubcategory || selectedDifficulty) && (
            <Button
              title="Clear"
              onPress={clearFilters}
              variant="text"
              size="small"
            />
          )}
        </View>

        {subcategories.length > 0 && (
          <View style={styles.filterSection}>
            <Text style={styles.filterSectionTitle}>Subcategories</Text>
            <View style={styles.filterChips}>
              {subcategories.map((subcategory) => (
                <View key={subcategory}>
                  {renderFilterChip(
                    subcategory,
                    subcategory,
                    selectedSubcategory === subcategory,
                    () => setSelectedSubcategory(selectedSubcategory === subcategory ? null : subcategory)
                  )}
                </View>
              ))}
            </View>
          </View>
        )}

        <View style={styles.filterSection}>
          <Text style={styles.filterSectionTitle}>Difficulty</Text>
          <View style={styles.filterChips}>
            {difficulties.map((difficulty) => (
              <View key={difficulty}>
                {renderFilterChip(
                  difficulty.charAt(0).toUpperCase() + difficulty.slice(1),
                  difficulty,
                  selectedDifficulty === difficulty,
                  () => setSelectedDifficulty(selectedDifficulty === difficulty ? null : difficulty)
                )}
              </View>
            ))}
          </View>
        </View>
      </View>

      <View style={styles.resultsContainer}>
        <View style={styles.resultsHeader}>
          <Text style={styles.resultsTitle}>
            {filteredFormulas.length} formula{filteredFormulas.length !== 1 ? 's' : ''}
          </Text>
        </View>
        
        <FlatList
          data={filteredFormulas}
          renderItem={renderFormulaCard}
          keyExtractor={(item) => item.id}
          showsVerticalScrollIndicator={false}
          contentContainerStyle={styles.formulasList}
        />
      </View>
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
    alignItems: 'center',
    padding: theme.spacing.lg,
    paddingBottom: theme.spacing.md,
  },
  backButton: {
    marginRight: theme.spacing.md,
  },
  headerContent: {
    flexDirection: 'row',
    alignItems: 'center',
    flex: 1,
  },
  categoryIcon: {
    width: 48,
    height: 48,
    borderRadius: theme.borderRadius.md,
    alignItems: 'center',
    justifyContent: 'center',
    marginRight: theme.spacing.md,
  },
  headerText: {
    flex: 1,
  },
  categoryTitle: {
    ...theme.typography.headlineSmall,
    color: theme.colors.surface[10],
    marginBottom: theme.spacing.xs,
  },
  categoryDescription: {
    ...theme.typography.bodyMedium,
    color: theme.colors.surface[7],
  },
  filtersContainer: {
    paddingHorizontal: theme.spacing.lg,
    paddingBottom: theme.spacing.lg,
  },
  filtersHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: theme.spacing.md,
  },
  filtersTitle: {
    ...theme.typography.titleMedium,
    color: theme.colors.surface[10],
  },
  filterSection: {
    marginBottom: theme.spacing.md,
  },
  filterSectionTitle: {
    ...theme.typography.labelLarge,
    color: theme.colors.surface[8],
    marginBottom: theme.spacing.sm,
  },
  filterChips: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: theme.spacing.sm,
  },
  filterChip: {
    paddingHorizontal: theme.spacing.md,
    paddingVertical: theme.spacing.sm,
    borderRadius: theme.borderRadius.full,
    backgroundColor: theme.colors.surface[2],
    borderWidth: 1,
    borderColor: theme.colors.surface[3],
  },
  filterChipSelected: {
    backgroundColor: theme.colors.primary[500],
    borderColor: theme.colors.primary[500],
  },
  filterChipText: {
    ...theme.typography.labelMedium,
    color: theme.colors.surface[8],
  },
  filterChipTextSelected: {
    color: theme.colors.surface[0],
  },
  resultsContainer: {
    flex: 1,
    paddingHorizontal: theme.spacing.lg,
  },
  resultsHeader: {
    marginBottom: theme.spacing.md,
  },
  resultsTitle: {
    ...theme.typography.titleMedium,
    color: theme.colors.surface[10],
  },
  formulasList: {
    paddingBottom: theme.spacing.lg,
  },
  formulaCard: {
    marginBottom: theme.spacing.md,
  },
  formulaHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: theme.spacing.sm,
  },
  formulaName: {
    ...theme.typography.titleMedium,
    color: theme.colors.surface[10],
    flex: 1,
  },
  difficultyBadge: {
    paddingHorizontal: theme.spacing.sm,
    paddingVertical: theme.spacing.xs,
    borderRadius: theme.borderRadius.sm,
  },
  difficultyText: {
    ...theme.typography.labelSmall,
    color: theme.colors.surface[0],
    textTransform: 'capitalize',
  },
  formulaText: {
    ...theme.typography.bodyLarge,
    color: theme.colors.primary[500],
    fontFamily: 'monospace',
    marginBottom: theme.spacing.sm,
  },
  formulaDescription: {
    ...theme.typography.bodyMedium,
    color: theme.colors.surface[7],
    marginBottom: theme.spacing.sm,
  },
  subcategoryBadge: {
    alignSelf: 'flex-start',
    backgroundColor: theme.colors.surface[2],
    paddingHorizontal: theme.spacing.sm,
    paddingVertical: theme.spacing.xs,
    borderRadius: theme.borderRadius.sm,
    marginBottom: theme.spacing.sm,
  },
  subcategoryText: {
    ...theme.typography.labelSmall,
    color: theme.colors.surface[8],
  },
  formulaTags: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: theme.spacing.xs,
  },
  tag: {
    backgroundColor: theme.colors.surface[2],
    paddingHorizontal: theme.spacing.sm,
    paddingVertical: theme.spacing.xs,
    borderRadius: theme.borderRadius.sm,
  },
  tagText: {
    ...theme.typography.labelSmall,
    color: theme.colors.surface[8],
  },
});