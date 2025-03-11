package o2.environment;

import engine.support.Vec2d;
import java.util.Arrays;
import java.util.List;
import jdk.internal.dynalink.support.BottomGuardingDynamicLinker;

public class WorldValues {
  private static final double BOTTOM = 1660;

  public static final List<Vec2d[]> LANDSCAPE_POLYGON_PTS = Arrays.asList(
      //1
      new Vec2d[]{
          new Vec2d(	0, 1587	),
          new Vec2d(	0, BOTTOM	),
          new Vec2d(	84, BOTTOM	),
          new Vec2d(	84, 1589	)
      },
      //2
      new Vec2d[]{
          new Vec2d(	68, 1588	),
          new Vec2d(	68, BOTTOM	),
          new Vec2d(	499, BOTTOM	),
          new Vec2d(	502, 1590	),
      },
      //3
      new Vec2d[]{
          new Vec2d(	478, 1591	),
          new Vec2d(	479, BOTTOM	),
          new Vec2d(	1256, BOTTOM	),
          new Vec2d(	1249, 1555	),
      },
      //4
      new Vec2d[]{
          new Vec2d(	1230, 1581	),
          new Vec2d(	1233, BOTTOM	),
          new Vec2d(	1618, BOTTOM	),
          new Vec2d(	1617, 1576	),
      },
      //5
      new Vec2d[]{
          new Vec2d(	1587, 1589	),
          new Vec2d(	1585, BOTTOM	),
          new Vec2d(	2277, BOTTOM	),
          new Vec2d(	2282, 1523	),

      },
      //6
      new Vec2d[]{
          new Vec2d(	2245, 1524	),
          new Vec2d(	2244, BOTTOM	),
          new Vec2d(	2552, BOTTOM	),
          new Vec2d(	2552, 1500	),

      },
      //7
      new Vec2d[]{
          new Vec2d(	2518, 1505	),
          new Vec2d(	2518, BOTTOM	),
          new Vec2d(	3011, BOTTOM	),
          new Vec2d(	3014, 1305	),

      },
      //8
      new Vec2d[]{
          new Vec2d(	2983, 1324	),
          new Vec2d(	2983, BOTTOM	),
          new Vec2d(	3679, BOTTOM	),
          new Vec2d(	3682, 920	),

      },
      //9
      new Vec2d[]{
          new Vec2d(	3639, 946	),
          new Vec2d(	3626, BOTTOM	),
          new Vec2d(	4189, BOTTOM	),
          new Vec2d(	4190, 776	),

      },
      //10
      new Vec2d[]{
          new Vec2d(	4146, 785	),
          new Vec2d(	4146, BOTTOM	),
          new Vec2d(	4560, BOTTOM	),
          new Vec2d(	4560, 713	),

      },
      //11
      new Vec2d[]{
          new Vec2d(	4526, 731	),
          new Vec2d(	4526, BOTTOM	),
          new Vec2d(	4934, BOTTOM	),
          new Vec2d(	4934, 612	),

      },

      // OTHER PLATFORMS

      //
      new Vec2d[]{
          new Vec2d(	439, 1362	),
          new Vec2d(	442, 1380	),
          new Vec2d(	783, 1462	),
          new Vec2d(	1093, 1389	),
          new Vec2d(	1178, 1320	),
      },
      //
      new Vec2d[]{
          new Vec2d(	509, 1381	),
          new Vec2d(	552, 1431	),
          new Vec2d(	960, 1495	),
          new Vec2d(	1005, 1374	),
      },
      //
      new Vec2d[]{
          new Vec2d(	914, 1390	),
          new Vec2d(	905, BOTTOM	),
          new Vec2d(	1152, BOTTOM	),
          new Vec2d(	1081, 1379	),
      },
      //
      new Vec2d[]{
          new Vec2d(	1104, 1327	),
          new Vec2d(	1112, 1362	),
          new Vec2d(	1274, 1320	),
          new Vec2d(	1264, 1285	),
      },
      //
      new Vec2d[]{
          new Vec2d(	1243, 1011	),
          new Vec2d(	1187, 1342	),
          new Vec2d(	1398, 1375	),
          new Vec2d(	1433, 1036	),
      },
      //
      new Vec2d[]{
          new Vec2d(	1109, 1229	),
          new Vec2d(	1109, 1248	),
          new Vec2d(	1233, 1240	),
          new Vec2d(	1234, 1221	),
      },
      //
      new Vec2d[]{
          new Vec2d(	1179, 1092	),
          new Vec2d(	1181, 1114	),
          new Vec2d(	1268, 1107	),
          new Vec2d(	1270, 1085	),
      },
      //
      new Vec2d[]{
          new Vec2d(	1389, 1196	),
          new Vec2d(	1372, 1359	),
          new Vec2d(	1575, 1501	),
          new Vec2d(	1553, 1188	),
      },
      //
      new Vec2d[]{
          new Vec2d(	1552, 1368	),
          new Vec2d(	1549, 1503	),
          new Vec2d(	1694, 1465	),
          new Vec2d(	1663, 1360	),
      },
      //
      new Vec2d[]{
          new Vec2d(	1671, 1447	),
          new Vec2d(	1672, 1468	),
          new Vec2d(	1764, 1455	),
          new Vec2d(	1760, 1426	),
      },
      //
      new Vec2d[]{
          new Vec2d(	1766, 1327	),
          new Vec2d(	1730, 1458	),
          new Vec2d(	1875, 1453	),
          new Vec2d(	1868, 1327	),
      }
  );

}
