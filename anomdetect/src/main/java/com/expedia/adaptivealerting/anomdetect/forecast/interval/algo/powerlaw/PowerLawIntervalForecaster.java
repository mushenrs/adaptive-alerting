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
package com.expedia.adaptivealerting.anomdetect.forecast.interval.algo.powerlaw;

import com.expedia.adaptivealerting.anomdetect.forecast.interval.IntervalForecast;
import com.expedia.adaptivealerting.anomdetect.forecast.interval.IntervalForecaster;
import com.expedia.metrics.MetricData;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import static com.expedia.adaptivealerting.anomdetect.util.AssertUtil.notNull;

@RequiredArgsConstructor
public class PowerLawIntervalForecaster implements IntervalForecaster {

    @Getter
    @NonNull
    private PowerLawIntervalForecasterParams params;

    @Override
    public IntervalForecast forecast(MetricData metricData, double pointForecast) {
        notNull(metricData, "metricData can't be null");

        val width = params.getAlpha() * Math.pow(pointForecast, params.getBeta());
        val weakWidth = params.getWeakMultiplier() * width;
        val strongWidth = params.getStrongMultiplier() * width;

        return new IntervalForecast(
                pointForecast + strongWidth,
                pointForecast + weakWidth,
                pointForecast - weakWidth,
                pointForecast - strongWidth);
    }

}
