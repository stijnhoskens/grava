package algorithm;

public enum SearchMethod { 
	
	depthFirst(SearchType.Blind), breadthFirst(SearchType.Blind), nonDeterministic(SearchType.Blind), 
	iterativeDeepening(SearchType.Blind), biDirectional(SearchType.Blind), 
	hillClimbing1(SearchType.Heuristic), beam(SearchType.Heuristic), 
	hillClimbing2(SearchType.Heuristic), greedy(SearchType.Heuristic), 
	uniformCost(SearchType.Cost), optimalUniformCost(SearchType.Optimal), EEUniformCost(SearchType.Optimal), 
	AStar(SearchType.Optimal), IDAStar(SearchType.Optimal), SMAStar(SearchType.Optimal); 
	
	private final SearchType type;
	
	private SearchMethod(SearchType type) {
		this.type = type;
	}
	
	public SearchType getSearchType() {
		return this.type;
	}
	
	public boolean isHeuristic() {
		return this.type == SearchType.Heuristic;
	}
	
	public boolean isOptimal() {
		return this.type == SearchType.Optimal;
	}
}
