package com.victor.ambient_creatures.world.entity.client.capybara;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.util.Mth;

public class CapybaraModel extends EntityModel<CapybaraRenderState>
{
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart left_ear;
    private final ModelPart right_ear;
    private final ModelPart left_front_leg;
    private final ModelPart right_front_leg;
    private final ModelPart left_hind_leg;
    private final ModelPart right_hind_leg;

    protected CapybaraModel(ModelPart root)
    {
        super(root);

        this.root = root.getChild(PartNames.ROOT);
        this.body = this.root.getChild(PartNames.BODY);
        this.head = this.body.getChild(PartNames.HEAD);
        this.left_ear = this.head.getChild(PartNames.LEFT_EAR);
        this.right_ear = this.head.getChild(PartNames.RIGHT_EAR);
        this.left_front_leg = this.body.getChild(PartNames.LEFT_FRONT_LEG);
        this.right_front_leg = this.body.getChild(PartNames.RIGHT_FRONT_LEG);
        this.left_hind_leg = this.body.getChild(PartNames.LEFT_HIND_LEG);
        this.right_hind_leg = this.body.getChild(PartNames.RIGHT_HIND_LEG);
    }

    @Override
    public void setupAnim(CapybaraRenderState state)
    {
        super.setupAnim(state);
        this.setHeadAngles(state.yRot, state.xRot);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30.0f, 30.0f);
        headPitch = Mth.clamp(headPitch, -25.0f, 45.0f);

        this.head.yRot = headYaw * 0.017453292F;
        this.head.xRot = headPitch * (float) -(Math.PI / 180.0);
    }
}
