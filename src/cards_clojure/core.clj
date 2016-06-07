(ns cards-clojure.core)

(def suits [:clubs :spades :hearts :diamonds])
(def ranks (range 1 14))

;"helper" functions
(defn ascending-count [num]
  (conj '()
        (inc(inc(inc num)))
        (inc(inc num))
        (inc num)
        num))

(defn drop-first-n [n hand]
  (drop n (sort (map :rank hand))))

(defn drop-last-n [n hand]
  (drop-last n (sort (map :rank hand))))

;creation functions + test hand
(defn create-deck []
  (set
    ;loops over all ranks for each suit
    (for [
          suit suits
          rank ranks]
      {:suit suit :rank rank})))

(def test-hand #{{:suit :hearts, :rank 3}
  {:suit :clubs, :rank 1}
  {:suit :hearts, :rank 2}
  {:suit :spades, :rank 3}
 {:suit :spades, :rank 1}})

(defn create-hands [deck]
  (set
    (for [card1 deck
          ;disj returns deck without card1
          card2 (disj deck card1)
          card3 (disj deck card1 card2)
          card4 (disj deck card1 card2 card3)
          card5 (disj deck card1 card2 card3 card4)]
      #{card1 card2 card3 card4 card5})))

(defn winning-hand [hand1 hand2]
  )

;poker hands

(defn royal-flush? [hand]
  (and (straight-flush? hand)
       (=
         (sort (map :rank hand))
         ('(1 10 11 12 13)))))

(defn straight-flush? [hand]
  (and (straight? hand) (flush? hand)))

(defn four-of-a-kind? [hand]
  (apply = (map :rank hand)))

(defn full-house? [hand]
  (or
    (and
      (apply = (drop-first-n 3 hand))
      (apply = (drop-last-n 2 hand)))
    (and
      (apply = (drop-first-n 2 hand))
      (apply = (drop-last-n 3 hand)))))

(defn flush? [hand]
  (= 1 (count (set (map :suit hand)))))

(defn high-straight? [hand]
  (= (sort (map :rank hand)) '(1 10 11 12 13)))

(defn straight? [hand]
  (or (= (sort (map :rank hand))
         (ascending-count (first(sort (map :rank hand)))))
      (high-straight? hand)))

(defn three-of-a-kind? [hand]
  (or
    (apply = (drop-first-n 2 hand))
    (apply = (drop-last-n 2 hand)))
  )

(defn two-pair? [hand]
  (and
    (apply = (drop-first-n 3 hand))
    (apply = (drop-last-n 3 hand))))

(defn pair? [hand]
  (or
    (apply = (drop-first-n 3 hand))
    (apply = (drop-last-n 3 hand))))

(defn -main []
  (let [deck (create-deck)
        hands (create-hands deck)
        flush-hands (filter flush? hands)]
    (println (count flush-hands))))