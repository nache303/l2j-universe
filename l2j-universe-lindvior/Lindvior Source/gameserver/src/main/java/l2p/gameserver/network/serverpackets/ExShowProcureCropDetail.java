package l2p.gameserver.network.serverpackets;

import l2p.gameserver.data.xml.holder.ResidenceHolder;
import l2p.gameserver.instancemanager.CastleManorManager;
import l2p.gameserver.model.entity.residence.Castle;
import l2p.gameserver.templates.manor.CropProcure;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * format
 * dd[dddc]
 * dd[dQQc] - Gracia Final
 */
public class ExShowProcureCropDetail extends L2GameServerPacket {
    private int _cropId;
    private Map<Integer, CropProcure> _castleCrops;

    public ExShowProcureCropDetail(int cropId) {
        _cropId = cropId;
        _castleCrops = new TreeMap<Integer, CropProcure>();

        List<Castle> castleList = ResidenceHolder.getInstance().getResidenceList(Castle.class);
        for (Castle c : castleList) {
            CropProcure cropItem = c.getCrop(_cropId, CastleManorManager.PERIOD_CURRENT);
            if (cropItem != null && cropItem.getAmount() > 0)
                _castleCrops.put(c.getId(), cropItem);
        }
    }

    @Override
    public void writeImpl() {
        writeEx449(0x78);

        writeD(_cropId); // crop id
        writeD(_castleCrops.size()); // size

        for (int manorId : _castleCrops.keySet()) {
            CropProcure crop = _castleCrops.get(manorId);
            writeD(manorId); // manor name
            writeQ(crop.getAmount()); // buy residual
            writeQ(crop.getPrice()); // buy price
            writeC(crop.getReward()); // reward type
        }
    }
}