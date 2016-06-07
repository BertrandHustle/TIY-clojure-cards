(ns cards-clojure.core)

(def suits [:clubs :spades :hearts :diamonds])
(def ranks (range 1 14))

(defn create-deck []
  (set
    ;loops over all ranks for each suit
    (for [
          suit suits
          rank ranks]
      {:suit suit :rank rank})))

(def test-hand #{{:suit :clubs, :rank 5}
  {:suit :hearts, :rank 5}
  {:suit :spades, :rank 4}
  {:suit :diamonds, :rank 5}})

(defn create-hands [deck]
  (set
    (for [card1 deck
          ;disj returns deck without card1
          card2 (disj deck card1)
          card3 (disj deck card1 card2)
          card4 (disj deck card1 card2 card3)]
      #{card1 card2 card3 card4})))

(defn flush? [hand]
  (= 1 (count (set (map :suit hand)))))

(defn four-of-a-kind? [hand]
  (apply = (map :rank test-hand)))

(defn three-of-a-kind? [hand]
  )

(defn two-pair? [hand]
  (and
    (apply = (drop 2 (sort (map :rank test-hand))))
    (apply = (drop-last 2 (sort (map :rank test-hand))))))

(defn -main []
  (let [deck (create-deck)
        hands (create-hands deck)
        flush-hands (filter flush? hands)]
    (println (count flush-hands))))