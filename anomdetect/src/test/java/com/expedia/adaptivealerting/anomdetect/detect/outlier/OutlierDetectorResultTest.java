/*
 * Copyright 2018-2019 Expedia Group, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.expedia.adaptivealerting.anomdetect.detect.outlier;

import com.expedia.adaptivealerting.anomdetect.detect.AnomalyLevel;
import com.expedia.adaptivealerting.anomdetect.detect.AnomalyThresholds;
import lombok.val;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public final class OutlierDetectorResultTest {
    private static final double TOLERANCE = 0.001;

    @Test
    public void coverageOnly() {
        val anomalyResult = new OutlierDetectorResult();
        anomalyResult.setAnomalyLevel(AnomalyLevel.NORMAL);

        val anomalyResult2 = new OutlierDetectorResult(AnomalyLevel.STRONG);
        anomalyResult2.setPredicted(10.0);
        anomalyResult2.setThresholds(new AnomalyThresholds(100.0, null, null, null));

        assertEquals(AnomalyLevel.STRONG, anomalyResult2.getAnomalyLevel());
        assertEquals(10.0, anomalyResult2.getPredicted(), TOLERANCE);
        assertNotNull(anomalyResult2.getThresholds());

        // Test constructor for anomalyLevel, trusted
        val anomalyResult3 = new OutlierDetectorResult(AnomalyLevel.STRONG, true);
        anomalyResult3.setPredicted(10.0);
        anomalyResult3.setThresholds(new AnomalyThresholds(100.0, null, null, null));

        assertEquals(AnomalyLevel.STRONG, anomalyResult3.getAnomalyLevel());
        assertEquals(true, anomalyResult3.isTrusted());
        assertEquals(10.0, anomalyResult3.getPredicted(), TOLERANCE);
        assertNotNull(anomalyResult3.getThresholds());

        // Test constructor for warmup, anomalyLevel, trusted
        val anomalyResult4 = new OutlierDetectorResult(true, AnomalyLevel.STRONG, true);
        anomalyResult4.setPredicted(10.0);
        anomalyResult4.setThresholds(new AnomalyThresholds(100.0, null, null, null));

        assertEquals(AnomalyLevel.STRONG, anomalyResult4.getAnomalyLevel());
        assertEquals(true, anomalyResult4.isTrusted());
        assertEquals(true, anomalyResult4.isWarmup());
        assertEquals(10.0, anomalyResult4.getPredicted(), TOLERANCE);
        assertNotNull(anomalyResult4.getThresholds());
    }
}
