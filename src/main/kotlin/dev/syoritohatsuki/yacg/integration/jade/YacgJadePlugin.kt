package dev.syoritohatsuki.yacg.integration.jade

import dev.syoritohatsuki.yacg.common.block.GeneratorBlock
import dev.syoritohatsuki.yacg.common.block.entity.GeneratorBlockEntity
import snownee.jade.api.IWailaClientRegistration
import snownee.jade.api.IWailaCommonRegistration
import snownee.jade.api.IWailaPlugin

object YacgJadePlugin : IWailaPlugin {
    override fun register(registration: IWailaCommonRegistration) {
        registration.registerBlockDataProvider(GeneratorComponentProvider.INSTANCE, GeneratorBlockEntity::class.java)
    }

    override fun registerClient(registration: IWailaClientRegistration) {
        registration.registerBlockComponent(GeneratorComponentProvider.INSTANCE, GeneratorBlock::class.java)
    }
}