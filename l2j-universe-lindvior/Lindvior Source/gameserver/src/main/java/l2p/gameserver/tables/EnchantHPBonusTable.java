/*
 * Copyright Mazaffaka Project (c) 2013.
 */

/*
 * Copyright Murzik Dev Team (c) 2013.
 */

package l2p.gameserver.tables;

import gnu.trove.map.hash.TIntObjectHashMap;
import l2p.gameserver.Config;
import l2p.gameserver.model.items.ItemInstance;
import l2p.gameserver.templates.item.ItemTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.StringTokenizer;

public class EnchantHPBonusTable {
    private static Logger _log = LoggerFactory.getLogger(EnchantHPBonusTable.class);
    private static EnchantHPBonusTable _instance = new EnchantHPBonusTable();
    private final TIntObjectHashMap<Integer[]> _armorHPBonus = new TIntObjectHashMap<Integer[]>();
    private int _onepieceFactor = 100;

    private EnchantHPBonusTable() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setValidating(false);
            factory.setIgnoringComments(true);

            File file = new File(Config.DATAPACK_ROOT, "data/enchant_bonus.xml");
            Document doc = factory.newDocumentBuilder().parse(file);

            for (Node n = doc.getFirstChild(); n != null; n = n.getNextSibling()) {
                if ("list".equalsIgnoreCase(n.getNodeName())) {
                    for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling()) {
                        NamedNodeMap attrs = d.getAttributes();
                        Node att;

                        if ("options".equalsIgnoreCase(d.getNodeName())) {
                            att = attrs.getNamedItem("onepiece_factor");

                            if (att == null) {
                                _log.info("EnchantHPBonusTable: Missing onepiece_factor, skipping");
                                continue;
                            }

                            _onepieceFactor = Integer.parseInt(att.getNodeValue());
                        } else if ("enchant_bonus".equalsIgnoreCase(d.getNodeName())) {
                            Integer grade;

                            att = attrs.getNamedItem("grade");

                            if (att == null) {
                                _log.info("EnchantHPBonusTable: Missing grade, skipping");
                                continue;
                            }

                            grade = Integer.parseInt(att.getNodeValue());
                            att = attrs.getNamedItem("values");

                            if (att == null) {
                                _log.info("EnchantHPBonusTable: Missing bonus id: " + grade + ", skipping");
                                continue;
                            }

                            StringTokenizer st = new StringTokenizer(att.getNodeValue(), ",");
                            int tokenCount = st.countTokens();
                            Integer[] bonus = new Integer[tokenCount];

                            for (int i = 0; i < tokenCount; i++) {
                                Integer value = Integer.decode(st.nextToken().trim());

                                if (value == null) {
                                    _log.info("EnchantHPBonusTable: Bad Hp value!! grade: " + grade + " token: " + i);

                                    value = 0;
                                }

                                bonus[i] = value;
                            }

                            _armorHPBonus.put(grade, bonus);
                        }
                    }
                }
            }

            _log.info("EnchantHPBonusTable: Loaded bonuses for " + _armorHPBonus.size() + " grades.");
        } catch (Exception e) {
            _log.warn("EnchantHPBonusTable: Lists could not be initialized.");
            e.printStackTrace();
        }
    }

    public static EnchantHPBonusTable getInstance() {
        if (_instance == null) {
            _instance = new EnchantHPBonusTable();
        }

        return _instance;
    }

    public void reload() {
        _instance = new EnchantHPBonusTable();
    }

    public final int getHPBonus(ItemInstance item) {
        final Integer[] values;

        if (item.getEnchantLevel() == 0) {
            return 0;
        }

        values = _armorHPBonus.get(item.getTemplate().getCrystalType().externalOrdinal);

        if ((values == null) || (values.length == 0)) {
            return 0;
        }

        int bonus = values[Math.min(item.getEnchantLevel(), values.length) - 1];

        if (item.getTemplate().getBodyPart() == ItemTemplate.SLOT_FULL_ARMOR) {
            bonus = (int) (bonus * _onepieceFactor / 100.0D);
        }

        return bonus;
    }
}
