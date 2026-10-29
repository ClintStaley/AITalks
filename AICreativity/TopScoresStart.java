import java.util.PriorityQueue;

/**
 * Utility for extracting the top 10% of a large array of test scores.
 *
 * Assumptions:
 *  - Scores are doubles in the range [0.0, 100.0], accurate to one decimal place.
 *  - "Top 10%" means the highest-scoring ceil(n * 0.10) entries.
 *  - The result is returned in ascending sorted order (lowest of the top scores
 *    first, highest last). Flip to descending easily if you'd rather see the
 *    best score first — see the comment at the return statement.
 */
public final class TopScores {
   public static double[] topTenPercent(double[] scores) {
      if (scores == null || scores.length == 0) {
         throw new IllegalArgumentException("scores must be a non-null, non-empty array");
      }

      for (double s : scores) {
         if (s < 0.0 || s > 100.0) {
            throw new IllegalArgumentException("score out of range [0, 100]: " + s);
         }
      }

      // Round to the nearest integer, but never go below 1 for a non-empty array.
      int k = (int) Math.max(1, Math.ceil(scores.length * 0.10));
      if (k < 1)
         k = 1;

      PriorityQueue<Double> minHeap = new PriorityQueue<>(k);

      for (double score : scores) {
         if (minHeap.size() < k) {
            minHeap.add(score);
         } else if (score > minHeap.peek()) {
            minHeap.poll();
            minHeap.add(score);
         }
      }

      double[] result = new double[minHeap.size()];
      // Draining a min-heap comes out in ascending order naturally.
      for (int i = 0; i < result.length; i++) {
            result[i] = minHeap.poll();
      }
      return result;
   }

    // Simple demonstration / manual test
   public static void main(String[] args) {
      double[] scores = {
         88.5, 92.1, 76.4, 99.9, 60.0, 85.5, 91.2, 100.0, 73.3, 67.8,
         95.5, 82.0, 78.9, 64.2, 89.1, 90.0, 71.1, 58.4, 96.6, 83.3
      };

      double[] topViaHeap = topTenPercent(scores);

      System.out.println("Top " + topViaHeap.length + " via heap (ascending):");
      for (double s : topViaHeap) {
         System.out.printf("%.1f%n", s);
      }
   }
}