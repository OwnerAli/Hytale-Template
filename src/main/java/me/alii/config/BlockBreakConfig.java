package me.alii.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import lombok.Getter;

@Getter
public class BlockBreakConfig {

    public static final BuilderCodec<BlockBreakConfig> CODEC = BuilderCodec.builder(BlockBreakConfig.class, BlockBreakConfig::new)
            .append(new KeyedCodec<>("AllowedBlock", Codec.STRING_ARRAY),
                    ((blockBreakConfig, strings) -> blockBreakConfig.allowedBlocks = strings),
                    (BlockBreakConfig::getAllowedBlocks)).add()
            .append(new KeyedCodec<>("ParticleId", Codec.STRING),
                    ((blockBreakConfig, particleId) -> blockBreakConfig.particleId = particleId),
                    (BlockBreakConfig::getParticleId)).add()
            .append(new KeyedCodec<>("SoundId", Codec.STRING),
                    ((blockBreakConfig, soundId) -> blockBreakConfig.soundId = soundId),
                    (BlockBreakConfig::getSoundId)).add()
            .build();

    private String[] allowedBlocks = {"Soil_Dirt"};
    private String particleId = "Alerted";
    private String soundId = "SFX_Hedera_Scream";
}
