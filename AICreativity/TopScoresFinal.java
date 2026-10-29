public final class TopScores {

   public static double[] topTenPercentHistogram(double[] scores) {
      if (scores == null || scores.length == 0) {
         throw new IllegalArgumentException("scores must be a non-null, non-empty array");
      }

      final int BUCKETS = 1001; // 0.0, 0.1, 0.2, ..., 100.0
      int[] counts = new int[BUCKETS];

      for (double s : scores) {
         if (s < 0.0 || s > 100.0) {
            throw new IllegalArgumentException("score out of range [0, 100]: " + s);
         }
         int bucket = (int) Math.round(s * 10.0);
         counts[bucket]++;
      }

      // Compute number of returns, but never go below 1 for a non-empty array.
      /* Claude version:
      int k = (int) Math.max(1, Math.ceil(scores.length * 0.10));   
      if (k < 1)
         k = 1;
      */
      int topCount = (int) Math.max(1, Math.round(scores.length * 0.10));

      /* Claude finds first bucket for top 10% 
      int cutoffBucket = BUCKETS - 1;
      for (int b = BUCKETS - 1; b >= 0; b--) {
         if (counts[b] > 0) {                   // Needless test
            accumulated += counts[b];
            if (accumulated >= topCount) {
               cutoffBucket = b;
               break;
            }
         }
      }
      // Then decides to "up" the return count to include entire cutoff bucket
      double[] result = new double[scores.length - accumulated + counts[cutoffBucket]];
      int next = 0;
      for (int bucket = cutoffBucket; bucket < BUCKETS; bucket++)
         for (int i = 0; i < counts[bucket]; i++)
            result[next++] = bucket / 10.0;
      */

      // My correction counts downward in results and buckets, and 
      // returns correct number
      double[] result = new double[topCount];
      int bucket = BUCKETS - 1;
      
      for (int count = topCount - 1; count >= 0; count--) {
         while (counts[bucket] == 0) {
            bucket--;
         }
         result[count] = bucket / 10.0;
         counts[bucket]--;
      }

      return result;
   }

/* Similar test main omitted */
