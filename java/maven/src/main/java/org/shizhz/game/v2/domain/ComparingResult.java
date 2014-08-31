package org.shizhz.game.v2.domain;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

/**
 * Created by zzshi on 8/30/14.
 */
public class ComparingResult {
    private static final String FORMAT = "%sA%sB";


    private Map<Integer, Character> preciseMatchedCharacters;

    private Set<Character> fuzzyMatchedCharacters;

    private ComparingResult(Map<Integer, Character> preciseMatch, Set<Character> fuzzyMatch) {
        preciseMatchedCharacters = preciseMatch == null ? ImmutableMap.<Integer, Character>of() : preciseMatch;
        fuzzyMatchedCharacters = fuzzyMatch == null ? Sets.<Character>newHashSet() : fuzzyMatch;
    }

    public Map<Integer, Character> getPreciseMatchedCharacters() {
        return preciseMatchedCharacters;
    }

    public Character preciseMatchedCharAt(int index) {
        return preciseMatchedCharacters.get(Integer.valueOf(index));
    }

    public boolean preciseMatchesAt(int index) {
        return preciseMatchedCharAt(index) != null;
    }

    public int numberOfPreciseMatch() {
        return preciseMatchedCharacters.keySet().size();
    }

    public int numberOfFuzzyMatch() {
        return fuzzyMatchedCharacters.size();
    }

    public boolean fuzzyMatches(Character character) {
       return fuzzyMatchedCharacters.contains(character);
    }

    @Override
    public String toString() {
        return String.format(FORMAT, numberOfPreciseMatch(), numberOfFuzzyMatch());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ComparingResult)) return false;

        return Maps.difference(ComparingResult.class.cast(obj).preciseMatchedCharacters, preciseMatchedCharacters).areEqual()
                && Sets.intersection(ComparingResult.class.cast(obj).fuzzyMatchedCharacters, fuzzyMatchedCharacters).size() == fuzzyMatchedCharacters.size();
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(preciseMatchedCharacters, fuzzyMatchedCharacters);
    }

    public static ComparingResult of(Map<Integer, Character> preciseMatch, Set<Character> fuzzyMatch) {
        return new ComparingResult(preciseMatch, fuzzyMatch);
    }

}
