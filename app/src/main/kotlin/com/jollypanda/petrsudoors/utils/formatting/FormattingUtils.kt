package com.jollypanda.petrsudoors.utils.formatting

import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.slots.Slot

/**
 * @author Yamushev Igor
 * @since  18.03.18
 */
object FormattingUtils {
    val PHONE_SLOTS = arrayOf(PredefinedSlots.hardcodedSlot('+'),
                              PredefinedSlots.hardcodedSlot('7'),
                              PredefinedSlots.digit(),
                              PredefinedSlots.digit(),
                              PredefinedSlots.digit(),
                              PredefinedSlots.hardcodedSlot(' ').withTags(Slot.TAG_DECORATION),
                              PredefinedSlots.digit(),
                              PredefinedSlots.digit(),
                              PredefinedSlots.digit(),
                              PredefinedSlots.hardcodedSlot(' ').withTags(Slot.TAG_DECORATION),
                              PredefinedSlots.digit(),
                              PredefinedSlots.digit(),
                              PredefinedSlots.hardcodedSlot(' ').withTags(Slot.TAG_DECORATION),
                              PredefinedSlots.digit(),
                              PredefinedSlots.digit())
}