package cn.jja8.knapsackToGo4.bukkit.basic.playerDataSupport.file;

import net.minecraft.server.v1_16_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class v1_16_R3 extends LockAndDataFileSupport{

    public v1_16_R3() throws IOException {
    }

    @Override
    cn.jja8.knapsackToGo4.bukkit.basic.playerDataSupport.file.LockAndDataFile getLockAndDataFile(File playerDataFile, Player player) {
        return new LockAndDataFile(playerDataFile,player);
    }

    public static class LockAndDataFile extends cn.jja8.knapsackToGo4.bukkit.basic.playerDataSupport.file.LockAndDataFile {
        Player player;
        public LockAndDataFile(File playerDataFile, Player player) {
            super(playerDataFile);
            this.player = player;
        }
        @Override
        public void saveToOutputStream(OutputStream outputStream){
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            ((CraftPlayer)player).getHandle().saveData(nbttagcompound);
            try {
                NBTCompressedStreamTools.a(nbttagcompound,outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void loadFromInputStream(InputStream inputStream) {
            try {
                ((CraftPlayer)player).getHandle().loadData(NBTCompressedStreamTools.a(inputStream));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
