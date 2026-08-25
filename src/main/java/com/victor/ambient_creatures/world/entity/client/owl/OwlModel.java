package com.victor.ambient_creatures.world.entity.client.owl;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;

public class OwlModel extends EntityModel<OwlRenderState>
{
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart nose;
    private final ModelPart left_wing;
    private final ModelPart right_wing;
    private final ModelPart left_leg;
    private final ModelPart left_foot;
    private final ModelPart right_leg;
    private final ModelPart right_foot;
    private final ModelPart tail;

    protected OwlModel(ModelPart root)
    {
        super(root);

        this.root = root.getChild(PartNames.ROOT);
        this.body = this.root.getChild(PartNames.BODY);
        this.head = this.body.getChild(PartNames.HEAD);
        this.nose = this.head.getChild(PartNames.NOSE);
        this.left_wing = this.body.getChild(PartNames.LEFT_WING);
        this.right_wing = this.body.getChild(PartNames.RIGHT_WING);
        this.left_leg = this.body.getChild(PartNames.LEFT_LEG);
        this.left_foot = this.left_leg.getChild(PartNames.LEFT_FOOT);
        this.right_leg = this.body.getChild(PartNames.RIGHT_LEG);
        this.right_foot = this.right_leg.getChild(PartNames.RIGHT_FOOT);
        this.tail = this.body.getChild(PartNames.TAIL);
    }

    @Override
    public void setupAnim(OwlRenderState state)
    {
        super.setupAnim(state);
        this.setHeadAngles(state.yRot, state.xRot);
    }

    private void setHeadAngles(float headYaw, float headPitch)
    {
        this.head.yRot = headYaw * (float)(Math.PI / 180.0);
        this.head.xRot = headPitch * (float)(Math.PI / 180.0);
    }
}
