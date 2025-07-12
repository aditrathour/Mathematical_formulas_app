import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TextInput,
  FlatList,
} from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { Ionicons } from '@expo/vector-icons';
import { theme } from '../theme';
import { Card } from '../components/Card';
import { Button } from '../components/Button';
import { categories, formulas } from '../data/formulas';
import { Formula, SearchResult } from '../types';

interface HomeScreenProps {
  navigation: any;
}

export const HomeScreen: React.FC<HomeScreenProps> = ({ navigation }) => {
  const [searchQuery, setSearchQuery] = useState('');
  const [searchResults, setSearchResults] = useState<SearchResult[]>([]);

  const handleSearch = (query: string) => {
    setSearchQuery(query);
    if (query.trim().length === 0) {
      setSearchResults([]);
      return;
    }

    const results: SearchResult[] = [];
    const lowerQuery = query.toLowerCase();

    formulas.forEach((formula) => {
      let relevance = 0;
      let matchType: 'name' | 'description' | 'tags' = 'tags';

      // Check name match
      if (formula.name.toLowerCase().includes(lowerQuery)) {
        relevance += 10;
        matchType = 'name';
      }

      // Check description match
      if (formula.description.toLowerCase().includes(lowerQuery)) {
        relevance += 5;
        matchType = 'description';
      }

      // Check tags match
      if (formula.tags.some(tag => tag.toLowerCase().includes(lowerQuery))) {
        relevance += 3;
        matchType = 'tags';
      }

      if (relevance > 0) {
        results.push({ formula, matchType, relevance });
      }
    });

    // Sort by relevance
    results.sort((a, b) => b.relevance - a.relevance);
    setSearchResults(results.slice(0, 10)); // Limit to 10 results
  };

  const renderCategoryCard = (category: any) => (
    <Card
      key={category.id}
      style={styles.categoryCard}
      onPress={() => navigation.navigate('Category', { category })}
      elevation={2}
    >
      <View style={[styles.categoryIcon, { backgroundColor: category.color }]}>
        <Ionicons name={category.icon as any} size={24} color="white" />
      </View>
      <Text style={styles.categoryName}>{category.name}</Text>
      <Text style={styles.categoryDescription}>{category.description}</Text>
      <View style={styles.categoryStats}>
        <Text style={styles.categoryCount}>
          {formulas.filter(f => f.category === category.id).length} formulas
        </Text>
      </View>
    </Card>
  );

  const renderSearchResult = ({ item }: { item: SearchResult }) => (
    <Card
      style={styles.searchResultCard}
      onPress={() => navigation.navigate('FormulaDetail', { formula: item.formula })}
      elevation={1}
    >
      <View style={styles.searchResultHeader}>
        <Text style={styles.searchResultName}>{item.formula.name}</Text>
        <View style={[styles.difficultyBadge, { backgroundColor: getDifficultyColor(item.formula.difficulty) }]}>
          <Text style={styles.difficultyText}>{item.formula.difficulty}</Text>
        </View>
      </View>
      <Text style={styles.searchResultFormula}>{item.formula.formula}</Text>
      <Text style={styles.searchResultDescription} numberOfLines={2}>
        {item.formula.description}
      </Text>
      <View style={styles.searchResultTags}>
        {item.formula.tags.slice(0, 3).map((tag, index) => (
          <View key={index} style={styles.tag}>
            <Text style={styles.tagText}>{tag}</Text>
          </View>
        ))}
      </View>
    </Card>
  );

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
        <Text style={styles.title}>Math Formulas</Text>
        <Text style={styles.subtitle}>Your comprehensive guide to mathematics</Text>
      </View>

      <View style={styles.searchContainer}>
        <View style={styles.searchInputContainer}>
          <Ionicons name="search" size={20} color={theme.colors.surface[6]} />
          <TextInput
            style={styles.searchInput}
            placeholder="Search formulas, concepts, or tags..."
            value={searchQuery}
            onChangeText={handleSearch}
            placeholderTextColor={theme.colors.surface[6]}
          />
          {searchQuery.length > 0 && (
            <Button
              title=""
              onPress={() => {
                setSearchQuery('');
                setSearchResults([]);
              }}
              variant="text"
              size="small"
              icon={<Ionicons name="close" size={20} color={theme.colors.surface[6]} />}
            />
          )}
        </View>
      </View>

      {searchQuery.length > 0 ? (
        <View style={styles.searchResultsContainer}>
          <Text style={styles.searchResultsTitle}>
            Search Results ({searchResults.length})
          </Text>
          <FlatList
            data={searchResults}
            renderItem={renderSearchResult}
            keyExtractor={(item) => item.formula.id}
            showsVerticalScrollIndicator={false}
            contentContainerStyle={styles.searchResultsList}
          />
        </View>
      ) : (
        <ScrollView style={styles.content} showsVerticalScrollIndicator={false}>
          <Text style={styles.sectionTitle}>Categories</Text>
          <View style={styles.categoriesGrid}>
            {categories.map(renderCategoryCard)}
          </View>
          
          <View style={styles.quickActions}>
            <Text style={styles.sectionTitle}>Quick Actions</Text>
            <View style={styles.quickActionsGrid}>
              <Button
                title="Favorites"
                onPress={() => navigation.navigate('Favorites')}
                variant="outlined"
                icon={<Ionicons name="heart" size={16} color={theme.colors.primary[500]} />}
                style={styles.quickActionButton}
              />
              <Button
                title="Recent"
                onPress={() => navigation.navigate('Recent')}
                variant="outlined"
                icon={<Ionicons name="time" size={16} color={theme.colors.primary[500]} />}
                style={styles.quickActionButton}
              />
            </View>
          </View>
        </ScrollView>
      )}
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: theme.colors.surface[1],
  },
  header: {
    padding: theme.spacing.lg,
    paddingBottom: theme.spacing.md,
  },
  title: {
    ...theme.typography.headlineLarge,
    color: theme.colors.surface[10],
    marginBottom: theme.spacing.xs,
  },
  subtitle: {
    ...theme.typography.bodyMedium,
    color: theme.colors.surface[7],
  },
  searchContainer: {
    paddingHorizontal: theme.spacing.lg,
    marginBottom: theme.spacing.lg,
  },
  searchInputContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: theme.colors.surface[0],
    borderRadius: theme.borderRadius.lg,
    paddingHorizontal: theme.spacing.md,
    paddingVertical: theme.spacing.sm,
    ...theme.shadows.elevation1,
  },
  searchInput: {
    flex: 1,
    marginLeft: theme.spacing.sm,
    ...theme.typography.bodyMedium,
    color: theme.colors.surface[10],
  },
  content: {
    flex: 1,
    paddingHorizontal: theme.spacing.lg,
  },
  sectionTitle: {
    ...theme.typography.titleLarge,
    color: theme.colors.surface[10],
    marginBottom: theme.spacing.md,
  },
  categoriesGrid: {
    marginBottom: theme.spacing.xl,
  },
  categoryCard: {
    marginBottom: theme.spacing.md,
  },
  categoryIcon: {
    width: 48,
    height: 48,
    borderRadius: theme.borderRadius.md,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: theme.spacing.md,
  },
  categoryName: {
    ...theme.typography.titleMedium,
    color: theme.colors.surface[10],
    marginBottom: theme.spacing.xs,
  },
  categoryDescription: {
    ...theme.typography.bodyMedium,
    color: theme.colors.surface[7],
    marginBottom: theme.spacing.md,
  },
  categoryStats: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  categoryCount: {
    ...theme.typography.labelMedium,
    color: theme.colors.primary[500],
  },
  quickActions: {
    marginBottom: theme.spacing.xl,
  },
  quickActionsGrid: {
    flexDirection: 'row',
    gap: theme.spacing.md,
  },
  quickActionButton: {
    flex: 1,
  },
  searchResultsContainer: {
    flex: 1,
    paddingHorizontal: theme.spacing.lg,
  },
  searchResultsTitle: {
    ...theme.typography.titleMedium,
    color: theme.colors.surface[10],
    marginBottom: theme.spacing.md,
  },
  searchResultsList: {
    paddingBottom: theme.spacing.lg,
  },
  searchResultCard: {
    marginBottom: theme.spacing.md,
  },
  searchResultHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: theme.spacing.sm,
  },
  searchResultName: {
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
  searchResultFormula: {
    ...theme.typography.bodyLarge,
    color: theme.colors.primary[500],
    fontFamily: 'monospace',
    marginBottom: theme.spacing.sm,
  },
  searchResultDescription: {
    ...theme.typography.bodyMedium,
    color: theme.colors.surface[7],
    marginBottom: theme.spacing.sm,
  },
  searchResultTags: {
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