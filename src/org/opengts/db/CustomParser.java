// ----------------------------------------------------------------------------
// Copyright 2007-2012, GeoTelematic Solutions, Inc.
// All rights reserved
// ----------------------------------------------------------------------------
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
// http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
// ----------------------------------------------------------------------------
// Change History:
//  2010/10/25  Martin D. Flynn
//     -Initial release
// ----------------------------------------------------------------------------
package org.opengts.db;

import java.lang.*;
import java.util.*;
import java.io.*;
import java.net.*;

import org.opengts.util.*;
import org.opengts.db.tables.*;

public interface CustomParser
{

    // ------------------------------------------------------------------------

    public static final String  PREFIX_     = "$";

    public static final String  ACCOUNT     = "$account";   // Account
    public static final String  DEVICE      = "$device";    // Device
    public static final String  DATA_TYPE   = "$type";      // String
    public static final String  DATA        = "$data";      // byte[]
    public static final String  DUPLEX      = "$duplex";    // Boolean
    public static final String  COUNT       = "$count";     // Integer
    public static final String  INDEX       = "$index";     // Integer

    // ------------------------------------------------------------------------
    // 

    /**
    *** ObjectMap: defined here to include executable initialization code within this interface.
    *** Example Usage:
    *** <pre>
    ***    Map&lt;String,Object&gt; map = CustomParser.ObjectMap.MAP.init(device,type,data,duplex);
    ***    ...
    ***    CustomParser.ObjectMap.MAP.clear(map);
    *** </pre>
    **/
    public enum ObjectMap {
        MAP;
        public Map<String,Object> init(Device dev, String type, byte data[], boolean duplex) {
            Map<String,Object> msp = new HashMap<String,Object>();
            msp.put(CustomParser.ACCOUNT  , (dev != null)? dev.getAccount() : null);    // Account
            msp.put(CustomParser.DEVICE   , dev);                                       // Device
            msp.put(CustomParser.DATA_TYPE, StringTools.trim(type));                    // String
            msp.put(CustomParser.DATA     , data    );                                  // byte[]
            msp.put(CustomParser.DUPLEX   , (duplex? Boolean.TRUE : Boolean.FALSE));    // Boolean
            return msp;
        }
        public Map<String,Object> init(Device dev, long type, byte data[], boolean duplex) {
            return this.init(dev, String.valueOf(type), data, duplex);
        }
        public Map<String,Object> clear(Map<String,Object> msp) {
            if (msp != null) {
                msp.remove(CustomParser.ACCOUNT  );
                msp.remove(CustomParser.DEVICE   );
                msp.remove(CustomParser.DATA     );
                msp.remove(CustomParser.DATA_TYPE);
                msp.remove(CustomParser.DUPLEX   );
            }
            return msp;
        }
    };

    // ------------------------------------------------------------------------

    /**
    *** Callback to parse raw data received from a remote tracking device through its
    *** device communication server.
    *** @param account  The assigned device Account instance
    *** @param device   The assigned Device instance
    *** @param type     The type of data contained in "data"
    *** @param data     The byte array containing the raw data
    *** @param props    A map where parsed data should be placed (to be inserted into the EventData record)
    *** @return The response which will be sent back to the device
    **/
    public byte[] parseData(
        Account account, Device device, 
        String type, byte data[], 
        Map<String,Object> props);

}
