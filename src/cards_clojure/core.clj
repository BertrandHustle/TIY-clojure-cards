(ns cards-clojure.core)

(def suits [:clubs :spades :hearts :diamonds])
(def ranks (range 1 14))

;clean this mess up
(defn ascending-count [num]
  (conj '()
        (inc(inc(inc num)))
        (inc(inc num))
        (inc num)
        num))

(defn create-deck []
  (set
    ;loops over all ranks for each suit
    (for [
          suit suits
          rank ranks]
      {:suit suit :rank rank})))

(def test-hand #{{:suit :clubs, :rank 2}
  {:suit :diamonds, :rank 3}
  {:suit :clubs, :rank 4}
  {:suit :clubs, :rank 5}})

(defn create-hands [deck]
  (set
    (for [card1 deck
          ;disj returns deck without card1
          card2 (disj deck card1)
          card3 (disj deck card1 card2)
          card4 (disj deck card1 card2 card3)]
      #{card1 card2 card3 card4})))

(defn straight-flush? [hand]
  (and (straight? hand) (flush? hand)))

(defn flush? [hand]
  (= 1 (count (set (map :suit hand)))))

(defn straight? [hand]
  (=
    (sort (map :rank hand))
    (ascending-count (first(sort (map :rank hand))))))

(defn four-of-a-kind? [hand]
  (apply = (map :rank hand)))

(defn three-of-a-kind? [hand]
  (or
    (apply = (drop 1 (sort (map :rank hand))))
    (apply = (drop-last 1 (sort (map :rank hand)))))
  )

(defn two-pair? [hand]
  (and
    (apply = (drop 2 (sort (map :rank hand))))
    (apply = (drop-last 2 (sort (map :rank hand))))))

(defn -main []
  (let [deck (create-deck)
        hands (create-hands deck)
        flush-hands (filter flush? hands)]
    (println (count flush-hands))))